package implementation;

import interfaces.AdministrativeModule;
import interfaces.Authenticator;
import interfaces.CinemaManagerModule;
import interfaces.ComponentManager;
import interfaces.MoviesManager;
import interfaces.ShowFinder;
import interfaces.TicketVendor;
import interfaces.UserAccountManager;
import model.Movie;
import model.User;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidCredentialsException;
import exceptions.InvalidUserRoleException;

public class CinemaSystem {

	private Authenticator authenticator;
	private CinemaManagerModule cinemaManagerModule;
	private AdministrativeModule administrativeModule;
	private UserAccountManager userAccountManager;
	private MoviesManager moviesManager;
	private ShowFinder showFinder;
	private TicketVendor ticketVendor;

	public CinemaSystem(String userLogin, String userPassword) {
		this.authenticator = new LoginPasswordAuthenticator(userLogin,
				userPassword);
	}

	public void authenticate() throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		User authenticatedUser = this.authenticator.authenticate();

		this.userAccountManager = new DbUserAccountManager(authenticatedUser);
		this.moviesManager = new DbMoviesManager(authenticatedUser);

		switch (authenticatedUser.getRole()) {

		case Administrator:
			this.administrativeModule = new AdministrativeModuleImpl(
					authenticatedUser);
			break;

		case CinemaManager:
			this.cinemaManagerModule = new CinemaManagerModuleImpl(
					authenticatedUser);
			this.showFinder = new DbShowFinder(authenticatedUser);
			break;

		case Cashier:
			this.showFinder = new DbShowFinder(authenticatedUser);
			this.ticketVendor = new DbTicketVendor(authenticatedUser);
			break;

		default:
			break;
		}
	}

	public ShowFinder getShowFinder() {
		return showFinder;
	}

	public ComponentManager<Movie> getMoviesManager() {
		return moviesManager;
	}

	public UserAccountManager getUserAccountManager() {
		return userAccountManager;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public CinemaManagerModule getCinemaManagerModule() {
		return cinemaManagerModule;
	}

	public AdministrativeModule getAdministrativeModule() {
		return administrativeModule;
	}

	public TicketVendor getTicketVendor() {
		return ticketVendor;
	}

}
