package implementation;

import interfaces.DAO;
import interfaces.TicketVendor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import model.Auditorium;
import model.Booking;
import model.Discount;
import model.Role;
import model.Seat;
import model.SeatTakenPair;
import model.Show;
import model.Ticket;
import model.User;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import config.AppConfig;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidArgumentsException;
import exceptions.InvalidUserRoleException;

class DbTicketVendor implements TicketVendor {

	private User authenticatedUser;
	private DAO<Discount> discountDAO;
	private DAO<Auditorium> auditoriumDAO;
	private DAO<Ticket> ticketDAO;
	private DAO<Booking> bookingDAO;
	private WebResource service;

	public DbTicketVendor(User user) throws InvalidUserRoleException {
		this.authenticatedUser = user;
		this.discountDAO = new DbDAO<>(Discount.class);
		this.auditoriumDAO = new DbDAO<>(Auditorium.class);
		this.ticketDAO = new DbDAO<>(Ticket.class);
		this.bookingDAO = new DbDAO<>(Booking.class);

		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(config);

		this.service = client.resource(UriBuilder.fromUri(
				AppConfig.restConnectionString).build());

	}

	@Override
	public List<Discount> getDiscounts()
			throws FailedDatabaseOperationException, InvalidUserRoleException {

		this.validateUserRole();

		return this.discountDAO.readAll();
	}

	@Override
	public List<SeatTakenPair> getSeats(Show show)
			throws FailedDatabaseOperationException {

		ClientResponse response = service.path("additional").path("seatmap")
				.path(String.valueOf(show.getId()))
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		WebServiceResponseValidator.validateResponseStatus(response);

		SeatTakenPair[] seatTakenPairs = response
				.getEntity(SeatTakenPair[].class);

		List<SeatTakenPair> seatTakenList = new ArrayList<>();
		for (SeatTakenPair seatTakenPair : seatTakenPairs) {
			seatTakenList.add(seatTakenPair);
		}

		return seatTakenList;
	}

	@Override
	public Ticket createTicket(Show show, Seat seat, Discount discount)
			throws InvalidUserRoleException, FailedDatabaseOperationException {

		this.validateUserRole();
		this.validateShowAgainstUserCinema(show);
		this.validateSeatAgainstShow(seat, show);

		double price = show.getPrice();

		if (discount != null) {
			price -= discount.getValue() / 100 * show.getPrice();
			price = (double) Math.round(price * 100) / 100;
		}

		Ticket createdTicket = new Ticket(price,
				this.authenticatedUser.getId(), show.getId(), seat.getId());

		return createdTicket;
	}

	@Override
	public List<Ticket> sellTickets(List<Ticket> tickets)
			throws FailedDatabaseOperationException, InvalidArgumentsException {

		this.validateTicketsList(tickets);

		ClientResponse response = service.path("rezerwowanie").path("sell")
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, tickets);

		WebServiceResponseValidator.validateResponseStatus(response);

		Ticket[] soldTickets = response.getEntity(Ticket[].class);
		List<Ticket> soldTicketList = new ArrayList<>();
		for (Ticket ticket : soldTickets) {
			this.ticketDAO.createForeign(ticket);
			soldTicketList.add(ticket);
		}

		return soldTicketList;
	}

	@Override
	public void printTickets(List<Ticket> tickets)
			throws FileNotFoundException, UnsupportedEncodingException,
			FailedDatabaseOperationException {

		PrintWriter writer = new PrintWriter("tickets.txt", "UTF-8");

		try {
			for (Ticket ticket : tickets) {
				this.ticketDAO.createForeign(ticket);
				writer.println();
				writer.println("Ticket ID: " + ticket.getId());
				writer.println("Movie title: "
						+ ticket.getShow().getMovie().getTitle());
				writer.println("Date: " + ticket.getShow().getDate());
				writer.println("Auditorium: "
						+ ticket.getShow().getAuditorium().getName());
				writer.println("Seat: " + ticket.getSeat().getNumber());
			}
		} catch (Exception e) {
			throw e;
		} finally {
			writer.close();
		}
	}

	@Override
	public int bookTickets(List<Ticket> tickets, String customerName,
			String customerLastName, String customerEmail)
			throws FailedDatabaseOperationException, InvalidArgumentsException {

		this.validateTicketsList(tickets);

		ClientResponse response = service.path("rezerwowanie").path("book")
				.path(customerName).path(customerLastName)
				.queryParam("customerEmail", customerEmail)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, tickets);

		WebServiceResponseValidator.validateResponseStatus(response);

		Integer bookingId = response.getEntity(Integer.class);

		return bookingId;
	}

	@Override
	public Map<Booking, List<Ticket>> getCustomerBookings(
			String customerLastName, String customerEmail)
			throws FailedDatabaseOperationException {

		List<Ticket> allTickets = this.ticketDAO.readAll();
		Map<Booking, List<Ticket>> bookingTicketMap = new HashMap<Booking, List<Ticket>>();

		for (Booking booking : this.bookingDAO.readAll()) {
			if (booking.getCustomerLastName().toLowerCase()
					.equals(customerLastName.toLowerCase())
					&& booking.getCustomerEmail().toLowerCase()
							.equals(customerEmail.toLowerCase())) {
				List<Ticket> tickets = new ArrayList<>();
				for (Ticket ticket : allTickets) {
					if (ticket.getBookingId() != null
							&& ticket.getBookingId().equals(booking.getId())) {
						tickets.add(ticket);
					}
				}

				bookingTicketMap.put(booking, tickets);
			}
		}

		return bookingTicketMap;
	}

	@Override
	public List<Ticket> realizeBooking(String customerLastName, int bookingId)
			throws FailedDatabaseOperationException {

		ClientResponse response = service.path("rezerwowanie").path("realize")
				.path(customerLastName).path(String.valueOf(bookingId))
				.type(MediaType.APPLICATION_JSON).put(ClientResponse.class);

		WebServiceResponseValidator.validateResponseStatus(response);

		Ticket[] soldTickets = response.getEntity(Ticket[].class);
		List<Ticket> soldTicketList = new ArrayList<>();
		for (Ticket ticket : soldTickets) {
			this.ticketDAO.createForeign(ticket);
			soldTicketList.add(ticket);
		}

		return soldTicketList;
	}

	@Override
	public void cancelBooking(String customerLastName, int bookingId)
			throws FailedDatabaseOperationException {

		ClientResponse response = service.path("rezerwowanie").path("cancel")
				.path(customerLastName).path(String.valueOf(bookingId))
				.type(MediaType.APPLICATION_JSON).put(ClientResponse.class);

		WebServiceResponseValidator.validateResponseStatus(response);

		boolean isCancellingSuccessful = response.getEntity(Boolean.class);

		if (isCancellingSuccessful != true) {
			throw new FailedDatabaseOperationException("Cancelling unsuccesful");
		}
	}

	private void validateUserRole() throws InvalidUserRoleException {
		if (this.authenticatedUser.getRole() != Role.Cashier) {
			throw new InvalidUserRoleException(
					"Only cashier can manage tickets");
		}
	}

	private void validateShowAgainstUserCinema(Show show)
			throws InvalidUserRoleException, FailedDatabaseOperationException {

		if (!this.auditoriumDAO.read(show.getAuditoriumId()).getCinemaId()
				.equals(this.authenticatedUser.getCinemaId())) {
			throw new InvalidUserRoleException(
					"Cannot manage foreign cinema shows");
		}
	}

	private void validateSeatAgainstShow(Seat seat, Show show)
			throws InvalidUserRoleException, FailedDatabaseOperationException {

		if (!seat.getAuditoriumId().equals(show.getAuditoriumId())) {
			throw new InvalidUserRoleException("Not matching auditorium");
		}
	}

	private void validateTicketsList(List<Ticket> tickets)
			throws InvalidArgumentsException {

		for (int i = 0; i < tickets.size(); i++) {
			for (int j = 0; j < tickets.size(); j++) {
				if (i != j
						&& tickets.get(i).getSeatId()
								.equals(tickets.get(j).getSeatId())) {
					throw new InvalidArgumentsException(
							"Ticket seats must be unique");
				}
			}
		}
	}
}
