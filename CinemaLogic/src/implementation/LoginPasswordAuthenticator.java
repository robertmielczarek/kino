package implementation;

import interfaces.Authenticator;
import interfaces.DAO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import model.Role;
import model.User;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.Statuses;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import config.AppConfig;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidCredentialsException;

class LoginPasswordAuthenticator implements Authenticator {

	private WebResource service;
	private DAO<User> userDAO;

	public LoginPasswordAuthenticator(String login, String password) {

		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(config);
		this.service = client.resource(UriBuilder
				.fromUri(AppConfig.restConnectionString).path("users")
				.path("authenticate").path(login).path(password).build());
		this.userDAO = new DbDAO<>(User.class);
	}

	@Override
	public User authenticate() throws InvalidCredentialsException,
			FailedDatabaseOperationException {

		ClientResponse response = this.service.accept(
				MediaType.APPLICATION_JSON).get(ClientResponse.class);

		if (response.getStatusInfo().getFamily() != Statuses.from(200)
				.getFamily()) {

			throw new FailedDatabaseOperationException();
		}

		User user = response.getEntity(User.class);

		if (user.getId() == null) {
			throw new InvalidCredentialsException();
		}

		if (user.getRole() != Role.Administrator) {
			this.userDAO.createForeign(user);
		}

		return user;
	}
}
