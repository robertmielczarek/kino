package implementation;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import model.Cinema;
import model.Genre;
import model.Movie;
import model.User;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import config.AppConfig;
import exceptions.FailedDatabaseOperationException;

abstract class DbStatistics {

	private WebResource service;

	public DbStatistics() {

		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(config);

		this.service = client
				.resource(UriBuilder.fromUri(AppConfig.restConnectionString)
						.path("bilets").build());
	}

	public int getSoldTicketsByMovie(Date startDate, Date endDate, Movie movie,
			Cinema cinema) throws FailedDatabaseOperationException {

		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		String cinemaIdString = cinema == null ? "" : String.valueOf(cinema
				.getId());

		ClientResponse response = null;
		try {
			response = this.service.path("movie").path(sdf.format(startDate))
					.path(sdf.format(endDate))
					.path(String.valueOf(movie.getId()))
					.queryParam("cinemaId", cinemaIdString)
					.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		WebServiceResponseValidator.validateResponseStatus(response);

		Integer count = response.getEntity(Integer.class);

		return count;
	}

	public int getSoldTicketsByDay(Date startDate, Date endDate, int dayOfWeek,
			Cinema cinema) throws FailedDatabaseOperationException {

		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		String cinemaIdString = cinema == null ? "" : String.valueOf(cinema
				.getId());

		ClientResponse response = null;
		try {
			response = this.service.path("day").path(sdf.format(startDate))
					.path(sdf.format(endDate)).path(String.valueOf(dayOfWeek))
					.queryParam("cinemaId", String.valueOf(cinemaIdString))
					.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		WebServiceResponseValidator.validateResponseStatus(response);

		Integer count = response.getEntity(Integer.class);

		return count;
	}

	public int getSoldTicketsByGenre(Date startDate, Date endDate, Genre genre,
			Cinema cinema) throws FailedDatabaseOperationException {

		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		String cinemaIdString = cinema == null ? "" : String.valueOf(cinema
				.getId());

		ClientResponse response = null;
		try {
			response = this.service.path("genre").path(sdf.format(startDate))
					.path(sdf.format(endDate)).path(genre.toString())
					.queryParam("cinemaId", String.valueOf(cinemaIdString))
					.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		WebServiceResponseValidator.validateResponseStatus(response);

		Integer count = response.getEntity(Integer.class);

		return count;
	}

	public int getSoldTicketsByCashier(Date startDate, Date endDate,
			User cashier) throws FailedDatabaseOperationException {

		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		ClientResponse response = null;
		try {
			response = this.service.path("cashier").path(sdf.format(startDate))
					.path(sdf.format(endDate))
					.path(String.valueOf(cashier.getId()))
					.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		WebServiceResponseValidator.validateResponseStatus(response);

		Integer count = response.getEntity(Integer.class);

		return count;
	}

	public int getSoldTicketsByCinema(Date startDate, Date endDate,
			Cinema cinema) throws FailedDatabaseOperationException {

		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		String cinemaIdString = cinema == null ? "" : String.valueOf(cinema
				.getId());

		ClientResponse response = null;
		try {
			response = this.service.path("cinema").path(sdf.format(startDate))
					.path(sdf.format(endDate))
					.queryParam("cinemaId", String.valueOf(cinemaIdString))
					.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		WebServiceResponseValidator.validateResponseStatus(response);

		Integer count = response.getEntity(Integer.class);

		return count;
	}

	public double getMoneyEarnedByCinema(Date startDate, Date endDate,
			Cinema cinema) throws FailedDatabaseOperationException {

		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		String cinemaIdString = cinema == null ? "" : String.valueOf(cinema
				.getId());

		ClientResponse response = null;
		try {
			response = this.service.path("money").path(sdf.format(startDate))
					.path(sdf.format(endDate))
					.queryParam("cinemaId", String.valueOf(cinemaIdString))
					.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		WebServiceResponseValidator.validateResponseStatus(response);

		Double money = response.getEntity(Double.class);

		return money;
	}
}
