package interfaces;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidArgumentsException;
import exceptions.InvalidUserRoleException;
import model.Booking;
import model.Discount;
import model.Seat;
import model.SeatTakenPair;
import model.Show;
import model.Ticket;

/**
 * The Interface TicketVendor. Provides method to sell, book and other
 * operations with tickets.
 */
public interface TicketVendor {

	/**
	 * Gets the pairs of seats and their availability in specified show.
	 * 
	 * @param show
	 *            the show to get seats
	 * @return the seats and availability pairs
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	List<SeatTakenPair> getSeats(Show show)
			throws FailedDatabaseOperationException;

	/**
	 * Gets all the discounts.
	 * 
	 * @return all the discounts in system
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 * @throws InvalidUserRoleException
	 *             the invalid user role exception
	 */
	List<Discount> getDiscounts() throws FailedDatabaseOperationException,
			InvalidUserRoleException;

	/**
	 * Creates the ticket locally.
	 * 
	 * @param show
	 *            the show on ticket
	 * @param seat
	 *            the seat on ticket
	 * @param discount
	 *            the discount on ticket
	 * @return the created ticket
	 * @throws InvalidUserRoleException
	 *             the invalid user role exception
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	Ticket createTicket(Show show, Seat seat, Discount discount)
			throws InvalidUserRoleException, FailedDatabaseOperationException;

	/**
	 * Sell tickets.
	 * 
	 * @param tickets
	 *            the tickets to sell
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 * @throws InvalidArgumentsException
	 *             the invalid arguments exception
	 */
	List<Ticket> sellTickets(List<Ticket> tickets)
			throws FailedDatabaseOperationException, InvalidArgumentsException;

	/**
	 * Prints the tickets to text document tickets.txt.
	 * 
	 * @param tickets
	 *            the tickets to print
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	void printTickets(List<Ticket> tickets) throws FileNotFoundException,
			UnsupportedEncodingException, FailedDatabaseOperationException;

	/**
	 * Book provided tickets.
	 * 
	 * @param tickets
	 *            the tickets to book
	 * @param customerName
	 *            the customer name to create booking
	 * @param customerLastName
	 *            the customer last name to create booking
	 * @param customerEmail
	 *            the customer email to create booking
	 * @throws FailedDatabaseOperationException
	 * @throws InvalidArgumentsException
	 */
	int bookTickets(List<Ticket> tickets, String customerName,
			String customerLastName, String customerEmail)
			throws FailedDatabaseOperationException, InvalidArgumentsException;

	/**
	 * Gets the customer bookings.
	 * 
	 * @param customerLastName
	 *            the customer last name to search
	 * @param customerEmail
	 *            the customer email to search
	 * @return the customer bookings
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	Map<Booking, List<Ticket>> getCustomerBookings(String customerLastName,
			String customerEmail) throws FailedDatabaseOperationException;

	/**
	 * Realize booking.
	 * 
	 * @param customerLastName
	 *            the customer last name on booking to realize
	 * @param bookingId
	 *            the booking id to realize
	 * @throws FailedDatabaseOperationException
	 */
	List<Ticket> realizeBooking(String customerLastName, int bookingId)
			throws FailedDatabaseOperationException;

	/**
	 * Cancel booking.
	 * 
	 * @param customerLastName
	 *            the customer last name on booking to cancel
	 * @param bookingId
	 *            the booking id to cancel
	 * @throws FailedDatabaseOperationException
	 */
	void cancelBooking(String customerLastName, int bookingId) throws FailedDatabaseOperationException;
}
