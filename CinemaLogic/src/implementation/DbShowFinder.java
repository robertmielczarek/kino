package implementation;

import interfaces.ShowFinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import webservice.entities.Seans;
import model.Auditorium;
import model.Cinema;
import model.Genre;
import model.Movie;
import model.Show;
import model.User;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import config.AppConfig;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidUserRoleException;

class DbShowFinder implements ShowFinder {

	private int cinemaId;

	private WebResource service;

	public DbShowFinder(User authenticatedUser) throws InvalidUserRoleException {
		switch (authenticatedUser.getRole()) {

		case CinemaManager:
		case Cashier:
			this.cinemaId = authenticatedUser.getCinemaId();
			break;

		default:
			throw new InvalidUserRoleException();
		}

		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(config);

		this.service = client.resource(UriBuilder
				.fromUri(AppConfig.restConnectionString).path("seans").build());
	}

	public List<Show> getShowsByTitle(String title)
			throws FailedDatabaseOperationException {

		ClientResponse response = this.service.path("title").path(title)
				.path(String.valueOf(this.cinemaId))
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(ClientResponse.class);

		WebServiceResponseValidator.validateResponseStatus(response);

		Seans[] seansList = response.getEntity(Seans[].class);

		List<Show> foundShows = new ArrayList<>();
		for (Seans seans : seansList) {
			foundShows.add(this.convertSeansToShow(seans));
		}

		return foundShows;
	}

	public List<Show> getShowsByDate(Date date)
			throws FailedDatabaseOperationException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		ClientResponse response = this.service.path("date")
				.path(simpleDateFormat.format(date))
				.path(String.valueOf(this.cinemaId))
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(ClientResponse.class);

		WebServiceResponseValidator.validateResponseStatus(response);

		Seans[] seansList = response.getEntity(Seans[].class);

		List<Show> foundShows = new ArrayList<>();
		for (Seans seans : seansList) {
			foundShows.add(this.convertSeansToShow(seans));
		}

		return foundShows;
	}

	public List<Show> getShowsByTime(Date time, int hoursDiff)
			throws FailedDatabaseOperationException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		ClientResponse response = this.service.path("time")
				.path(simpleDateFormat.format(time))
				.path(String.valueOf(hoursDiff))
				.path(String.valueOf(this.cinemaId))
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(ClientResponse.class);

		WebServiceResponseValidator.validateResponseStatus(response);

		Seans[] seansList = response.getEntity(Seans[].class);

		List<Show> foundShows = new ArrayList<>();
		for (Seans seans : seansList) {
			foundShows.add(this.convertSeansToShow(seans));
		}

		return foundShows;
	}

	public List<Show> getShowsByGenre(Genre genre)
			throws FailedDatabaseOperationException {

		ClientResponse response = this.service.path("genre")
				.path(genre.toString()).path(String.valueOf(this.cinemaId))
				.accept(MediaType.APPLICATION_JSON_TYPE)
				.get(ClientResponse.class);

		WebServiceResponseValidator.validateResponseStatus(response);

		Seans[] seansList = response.getEntity(Seans[].class);

		List<Show> foundShows = new ArrayList<>();
		for (Seans seans : seansList) {
			foundShows.add(this.convertSeansToShow(seans));
		}

		return foundShows;
	}

	private Show convertSeansToShow(Seans seans) {

		Show show = new Show();
		Auditorium auditorium = new Auditorium();
		Cinema cinema = new Cinema();
		Movie movie = new Movie();

		cinema.setId(seans.getIDAuditorium().getIDCinema().getIDCinema());
		cinema.setName(seans.getIDAuditorium().getIDCinema().getName());
		cinema.setCity(seans.getIDAuditorium().getIDCinema().getCity());

		auditorium.setId(seans.getIDAuditorium().getIDhall());
		auditorium.setCinemaId(cinema.getId());
		auditorium.setName(seans.getIDAuditorium().getName());
		auditorium.setCinema(cinema);

		movie.setId(seans.getIDMovie().getIDMovie());
		movie.setTitle(seans.getIDMovie().getTitle());
		movie.setGenre(Genre.valueOf((seans.getIDMovie().getGenre())));
		movie.setLength(seans.getIDMovie().getLenght());
		movie.setDescription(seans.getIDMovie().getDescription());

		show.setId(seans.getIDShow());
		show.setAuditoriumId(auditorium.getId());
		show.setMovieId(movie.getId());
		show.setAuditorium(auditorium);
		show.setMovie(movie);
		show.setDate(seans.getDate());
		show.setPrice(seans.getPrice());

		return show;
	}
}
