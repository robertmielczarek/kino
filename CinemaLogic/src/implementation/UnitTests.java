package implementation;

import interfaces.Authenticator;
import interfaces.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import model.Auditorium;
import model.Booking;
import model.Cinema;
import model.Discount;
import model.Genre;
import model.Movie;
import model.Role;
import model.Seat;
import model.SeatTakenPair;
import model.Show;
import model.Ticket;
import model.TicketStatus;
import model.User;

import org.junit.Assert;
import org.junit.Test;

import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidArgumentsException;
import exceptions.InvalidCredentialsException;
import exceptions.InvalidUserRoleException;

public class UnitTests {

	String userName = "testowe imie";
	String userLastName = "testowe nazwisko";
	String userPhoneNumber = "+48 551 23 12";
	String userAddress = "ul. testowa 8, 95-123 Warszawa";
	String userLogin = UUID.randomUUID().toString().substring(0, 8);
	String userPassword = "testowe haslo";

	String cinemaName = "nazwa kina testowa";
	String cinemaCity = "miasto testowe";

	String movieTitle = "testowy tytul";
	String movieDescription = "testowy opis filmu";
	Genre movieGenre = Genre.Action;
	String movieLength = "1h 50m";

	String auditoriumName = "testowa nazwa sali";

	double showPrice = 20.55;

	String discountName = "testowa znizka";
	double discountValue = 23.50;

	String bookingCustomerName = "testowe imie klienta";
	String bookingCustomerLastName = "testowe nazwisko klienta";
	String bookingCustomerEmail = "nowyemail@email.com";

	private User createTestUser(Role role, Integer cinemaId)
			throws FailedDatabaseOperationException {

		User createdUser = null;
		if (role == Role.Administrator) {
			createdUser = new User(0, this.userName, this.userLastName,
					this.userPhoneNumber, this.userAddress, role, UUID
							.randomUUID().toString().substring(0, 8),
					this.userPassword);
		} else {
			if (cinemaId == null) {
				cinemaId = this.createTestCinema().getId();
			}

			createdUser = new User(cinemaId, this.userName, this.userLastName,
					this.userPhoneNumber, this.userAddress, role, UUID
							.randomUUID().toString().substring(0, 8),
					this.userPassword);
		}

		DAO<User> userDAO = new DbDAO<User>(User.class);

		createdUser.setId(userDAO.create(createdUser));

		return createdUser;
	}

	private User createTestUser(Role role)
			throws FailedDatabaseOperationException {

		return this.createTestUser(role, null);
	}

	private Cinema createTestCinema() throws FailedDatabaseOperationException {
		Cinema createdCinema = new Cinema(this.cinemaName, this.cinemaCity);

		DAO<Cinema> cinemaDAO = new DbDAO<Cinema>(Cinema.class);

		createdCinema.setId(cinemaDAO.create(createdCinema));

		return createdCinema;
	}

	private Movie createTestMovie(String title, Genre genre)
			throws FailedDatabaseOperationException {

		if (title == null) {
			title = this.movieTitle;
		}

		if (genre == null) {
			genre = this.movieGenre;
		}

		Movie createdMovie = new Movie(title, this.movieDescription, genre,
				this.movieLength);

		DAO<Movie> movieDAO = new DbDAO<>(Movie.class);

		createdMovie.setId(movieDAO.create(createdMovie));

		return createdMovie;
	}

	private Auditorium createTestAuditorium(int cinemaId)
			throws FailedDatabaseOperationException {

		if (cinemaId == 0) {
			cinemaId = this.createTestCinema().getId();
		}

		Auditorium createdAuditorium = new Auditorium(cinemaId,
				this.auditoriumName);

		DAO<Auditorium> auditoriumDAO = new DbDAO<>(Auditorium.class);

		createdAuditorium.setId(auditoriumDAO.create(createdAuditorium));

		return createdAuditorium;
	}

	private Show createTestShow(int cinemaId, String movieTitle,
			Date movieDate, Genre genre, int movieId, double price,
			int auditoriumId) throws FailedDatabaseOperationException {

		if (cinemaId == 0) {
			cinemaId = this.createTestCinema().getId();
		}

		if (auditoriumId == 0) {
			auditoriumId = this.createTestAuditorium(cinemaId).getId();
		}

		if (price == 0) {
			price = this.showPrice;
		}

		if (movieTitle == null) {
			movieTitle = this.movieTitle;
		}

		if (movieDate == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.HOUR, 15);
			movieDate = new Date(calendar.getTimeInMillis());
		}

		if (movieId == 0) {
			movieId = this.createTestMovie(movieTitle, genre).getId();
		}

		Show createdShow = new Show(auditoriumId, movieId, movieDate, price);

		DAO<Show> showDAO = new DbDAO<>(Show.class);

		createdShow.setId(showDAO.create(createdShow));

		return createdShow;
	}

	private CinemaSystem createCinemaSystemAndLogin(String login,
			String password) throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		CinemaSystem cinemaSystem = new CinemaSystem(login, password);
		cinemaSystem.authenticate();

		return cinemaSystem;
	}

	private Seat createTestSeat(int seatNumber, int auditoriumId)
			throws FailedDatabaseOperationException {

		if (auditoriumId == 0) {
			auditoriumId = this.createTestAuditorium(0).getId();
		}

		Seat createdSeat = new Seat(auditoriumId, seatNumber);

		DAO<Seat> seatDAO = new DbDAO<>(Seat.class);

		createdSeat.setId(seatDAO.create(createdSeat));

		return createdSeat;
	}

	private Discount createTestDiscount(double discountValue)
			throws FailedDatabaseOperationException {

		if (discountValue == 0) {
			discountValue = this.discountValue;
		}

		Discount createdDiscount = new Discount(this.discountName,
				discountValue);

		DAO<Discount> discountDAO = new DbDAO<>(Discount.class);

		createdDiscount.setId(discountDAO.create(createdDiscount));

		return createdDiscount;
	}

	private Ticket createTestTicket(int cinemaId, int seatNumber, int movieId,
			int cashierId, int showId, Date date, int seatId, int bookingId,
			double price, Genre genre) throws FailedDatabaseOperationException {

		if (genre == null) {
			genre = this.movieGenre;
		}

		if (price == 0) {
			price = this.showPrice;
		}

		if (cinemaId == 0) {
			cinemaId = this.createTestCinema().getId();
		}

		if (movieId == 0) {
			movieId = this.createTestMovie(null, genre).getId();
		}

		if (cashierId == 0) {
			cashierId = this.createTestUser(Role.Cashier).getId();
		}

		if (date == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			date = new Date(calendar.getTimeInMillis());
		}

		if (seatId == 0) {
			seatId = this.createTestSeat(seatNumber, 0).getId();
		}

		if (showId == 0) {
			showId = this.createTestShow(cinemaId, null, null, null, movieId,
					0, 0).getId();
		}
		Ticket createdTicket = null;
		if (bookingId == 0) {
			createdTicket = new Ticket(price, TicketStatus.Sold, date,
					cashierId, showId, seatId);
		} else {
			createdTicket = new Ticket(price, TicketStatus.Sold, date,
					cashierId, showId, seatId, bookingId);
		}

		DAO<Ticket> ticketDAO = new DbDAO<>(Ticket.class);
		try {
			createdTicket.setId(ticketDAO.create(createdTicket));
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Create ticket operation failed");
		}

		return createdTicket;
	}

	private Ticket createTestTicket(int cinemaId, int seatNumber, int movieId,
			int cashierId, int showId, Date date, int seatId, int bookingId,
			double price) throws FailedDatabaseOperationException {

		return createTestTicket(cinemaId, seatNumber, movieId, cashierId,
				showId, date, seatId, bookingId, price, null);
	}

	private Ticket createTestTicket(int cinemaId, int seatNumber, int movieId,
			int cashierId, int showId, Date date, int seatId, int bookingId)
			throws FailedDatabaseOperationException {

		return createTestTicket(cinemaId, seatNumber, movieId, cashierId,
				showId, date, seatId, bookingId, 0);
	}

	private Booking createTestBooking(String customerName,
			String customerLastName, String customerEmail)
			throws FailedDatabaseOperationException {

		if (customerName == null) {
			customerName = "testowe imie";
		}

		if (customerLastName == null) {
			customerLastName = "testowe nazwisko";
		}

		if (customerEmail == null) {
			customerEmail = UUID.randomUUID().toString().substring(0, 8);
		}

		Booking createdBooking = new Booking(false, customerName,
				customerLastName, customerEmail);

		DAO<Booking> bookingDAO = new DbDAO<>(Booking.class);

		createdBooking.setId(bookingDAO.create(createdBooking));

		return createdBooking;
	}

	@Test
	public void testDbDAOUserCreateAndRead()
			throws FailedDatabaseOperationException {

		// arrange
		Cinema createdCinema = this.createTestCinema();

		User expectedUser = new User(createdCinema.getId(), this.userName,
				this.userLastName, this.userPhoneNumber, this.userAddress,
				Role.CinemaManager, this.userLogin, this.userPassword);

		User actualUser = null;

		// act
		DAO<User> userDAO = new DbDAO<>(User.class);

		try {
			expectedUser.setId(userDAO.create(expectedUser));
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Create user operation failed");
		}

		try {
			actualUser = userDAO.read(expectedUser.getId());
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read user operation failed");
		}

		// assert
		Assert.assertEquals(expectedUser, actualUser);
	}

	@Test
	public void testDbDAOUserReadAll() throws FailedDatabaseOperationException {

		// arrange
		Cinema createdCinema = this.createTestCinema();

		User expectedCinemaManager = new User(createdCinema.getId(),
				this.userName, this.userLastName, this.userPhoneNumber,
				this.userAddress, Role.CinemaManager, this.userLogin,
				this.userPassword);

		User expectedAdministrator = new User(0, "imie2", "nazwisko2",
				"456221785", "ul. druga 8, warszawa", Role.Administrator, UUID
						.randomUUID().toString().substring(0, 8), "haslo2");

		User expectedCashier = new User(createdCinema.getId(), "imie3",
				"nazwisko3", "45622155435", "ul. trzecia 003a, £Ûdü",
				Role.Cashier, UUID.randomUUID().toString().substring(0, 8),
				"haslo3");

		int expectedUsersListSize = 3;
		int actualUsersListSize = 0;

		// act
		DAO<User> userDAO = new DbDAO<>(User.class);

		List<User> allUsersBeforeCreate = null;
		try {
			allUsersBeforeCreate = userDAO.readAll();
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read all users operation failed");
		}

		try {
			expectedAdministrator.setId(userDAO.create(expectedAdministrator));
			expectedCinemaManager.setId(userDAO.create(expectedCinemaManager));
			expectedCashier.setId(userDAO.create(expectedCashier));
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Create user operation failed");
		}

		expectedAdministrator.setCinemaId(null);

		List<User> allUsersAfterCreate = null;
		try {
			allUsersAfterCreate = userDAO.readAll();
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read all users operation failed");
		}

		allUsersAfterCreate.removeAll(allUsersBeforeCreate);
		actualUsersListSize = allUsersAfterCreate.size();

		// assert
		Assert.assertEquals(expectedUsersListSize, actualUsersListSize);
		Assert.assertTrue(allUsersAfterCreate.contains(expectedCinemaManager));
		Assert.assertTrue(allUsersAfterCreate.contains(expectedAdministrator));
		Assert.assertTrue(allUsersAfterCreate.contains(expectedCashier));
	}

	@Test
	public void testDbDAOUserUpdate() throws FailedDatabaseOperationException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);

		User expectedUser = new User(createdUser.getId(),
				createdUser.getCinemaId(), "edytowane imie",
				"edytowane nazwisko", "456778512",
				"ul. testowa 4, 95-123 £Ûdü", Role.Cashier, UUID.randomUUID()
						.toString().substring(0, 8), "edytowane haslo");

		User actualUser = null;

		// act
		DAO<User> userDAO = new DbDAO<>(User.class);

		try {
			userDAO.update(expectedUser);
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Update user operation failed");
		}

		try {
			actualUser = userDAO.read(expectedUser.getId());
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read user operation failed");
		}

		// assert
		Assert.assertEquals(expectedUser, actualUser);

	}

	@Test
	public void testDbDAOUserDelete() throws FailedDatabaseOperationException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);

		boolean isExceptionThrown = false;

		// act
		DAO<User> userDAO = new DbDAO<>(User.class);

		try {
			userDAO.read(createdUser.getId());
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read user operation failed");
		}

		try {
			userDAO.delete(createdUser.getId());
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Delete user operation failed");
		}

		try {
			userDAO.read(createdUser.getId());
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrown = true;
			} else {
				Assert.fail("Read user operation failed");
			}
		}

		// assert
		Assert.assertTrue(isExceptionThrown);
	}

	@Test
	public void testDbDAOUserCreateForeignCinema()
			throws FailedDatabaseOperationException {

		// arrange
		Cinema expectedCinema = this.createTestCinema();

		User createdUser = new User(expectedCinema.getId(), this.userName,
				this.userLastName, this.userPhoneNumber, this.userAddress,
				Role.CinemaManager, this.userLogin, this.userPassword);

		Cinema actualCinema = null;
		User actualUser = null;

		// act
		DAO<User> userDAO = new DbDAO<User>(User.class);

		try {
			createdUser.setId(userDAO.create(createdUser));
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Create user operation failed");
		}

		try {
			actualUser = userDAO.read(createdUser.getId());
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read user operation failed");
		}

		actualCinema = actualUser.getCinema();

		// assert
		Assert.assertEquals(expectedCinema, actualCinema);
	}

	@Test
	public void testDbDAOAuditoriumCreateAndRead()
			throws FailedDatabaseOperationException {

		// arrange
		Cinema createdCinema = this.createTestCinema();

		Auditorium expectedAuditorium = new Auditorium(createdCinema.getId(),
				this.auditoriumName);

		Auditorium actualAuditorium = null;

		// act
		DAO<Auditorium> auditoriumDAO = new DbDAO<>(Auditorium.class);

		try {
			expectedAuditorium.setId(auditoriumDAO.create(expectedAuditorium));
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add auditorium operation failed");
		}

		try {
			actualAuditorium = auditoriumDAO.read(expectedAuditorium.getId());
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read auditorium operation failed");
		}

		// assert
		Assert.assertEquals(expectedAuditorium, actualAuditorium);
	}

	@Test
	public void testDbDAOShowCreateAndRead()
			throws FailedDatabaseOperationException {

		// arrange
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		Date date = new Date(calendar.getTimeInMillis());

		Show expectedShow = new Show(this.createTestAuditorium(0).getId(), this
				.createTestMovie(null, null).getId(), date, this.showPrice);

		Show actualShow = null;

		// act
		DAO<Show> showDAO = new DbDAO<>(Show.class);

		try {
			expectedShow.setId(showDAO.create(expectedShow));
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add show operation failed");
		}

		try {
			actualShow = showDAO.read(expectedShow.getId());
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read show operation failed");
		}

		// assert
		Assert.assertEquals(expectedShow, actualShow);
	}

	@Test
	public void testLoginPasswordAuthenticatorValidCredentials()
			throws FailedDatabaseOperationException {

		// arrange
		User expectedUser = this.createTestUser(Role.Cashier);

		User actualUser = null;

		// act
		Authenticator authenticator = new LoginPasswordAuthenticator(
				expectedUser.getLogin(), expectedUser.getPassword());

		try {
			actualUser = authenticator.authenticate();
		} catch (InvalidCredentialsException e) {
			Assert.fail("Credentials not matching");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Authenticate operation failed");
		}

		// assert
		Assert.assertEquals(expectedUser, actualUser);
	}

	@Test
	public void testLoginPasswordAuthenticatorInvalidLogin()
			throws FailedDatabaseOperationException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		String invalidLogin = new String(this.userLogin + "123");
		boolean isExceptionThrown = false;

		// act
		Authenticator authenticator = new LoginPasswordAuthenticator(
				invalidLogin, createdUser.getPassword());

		try {
			authenticator.authenticate();
		} catch (InvalidCredentialsException e) {
			isExceptionThrown = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Authenticate operation failed");
		}

		// assert
		Assert.assertTrue(isExceptionThrown);
	}

	@Test
	public void testLoginPasswordAuthenticatorInvalidPassword()
			throws FailedDatabaseOperationException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		String invalidPassword = new String(this.userPassword + "123");
		boolean isExceptionThrown = false;

		// act
		Authenticator authenticator = new LoginPasswordAuthenticator(
				createdUser.getLogin(), invalidPassword);

		try {
			authenticator.authenticate();
		} catch (InvalidCredentialsException e) {
			isExceptionThrown = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Authenticate operation failed");
		}

		// assert
		Assert.assertTrue(isExceptionThrown);
	}

	@Test
	public void testLoginPasswordAuthenticatorInvalidLoginAndPassword() {

		// arrange
		String invalidLogin = new String(this.userLogin + "123");
		String invalidPassword = new String(this.userPassword + "123");

		boolean isExceptionThrown = false;

		// act
		Authenticator authenticator = new LoginPasswordAuthenticator(
				invalidLogin, invalidPassword);

		try {
			authenticator.authenticate();
		} catch (InvalidCredentialsException e) {
			isExceptionThrown = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Authenticate operation failed");
		}

		// assert
		Assert.assertTrue(isExceptionThrown);
	}

	@Test
	public void testCinemaSystemLoginAsCinemaManager()
			throws FailedDatabaseOperationException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		boolean isAuthenticated = false;

		// act
		CinemaSystem cinemaSystem = new CinemaSystem(createdUser.getLogin(),
				createdUser.getPassword());

		try {
			cinemaSystem.authenticate();
			isAuthenticated = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Authenticate operation failed");
		} catch (InvalidCredentialsException e) {
			Assert.fail("Not matching user credentials");
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		}

		// assert
		Assert.assertTrue(isAuthenticated);
	}

	@Test
	public void testCinemaSystemLoginAsCashier()
			throws FailedDatabaseOperationException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		boolean isAuthenticated = false;

		// act
		CinemaSystem cinemaSystem = new CinemaSystem(createdUser.getLogin(),
				createdUser.getPassword());

		try {
			cinemaSystem.authenticate();
			isAuthenticated = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Authenticate operation failed");
		} catch (InvalidCredentialsException e) {
			Assert.fail("Not matching user credentials");
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		}

		// assert
		Assert.assertTrue(isAuthenticated);
	}

	@Test
	public void testCinemaSystemLoginAsAdministrator()
			throws FailedDatabaseOperationException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		boolean isAuthenticated = false;

		// act
		CinemaSystem cinemaSystem = new CinemaSystem(createdUser.getLogin(),
				createdUser.getPassword());

		try {
			cinemaSystem.authenticate();
			isAuthenticated = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Authenticate operation failed");
		} catch (InvalidCredentialsException e) {
			Assert.fail("Not matching user credentials");
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		}

		// assert
		Assert.assertTrue(isAuthenticated);
	}

	@Test
	public void testCinemaSystemUserAccountManagerGetAuthenticatedUser()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User expectedUser = this.createTestUser(Role.CinemaManager);
		User actualUser = null;
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				expectedUser.getLogin(), expectedUser.getPassword());

		// act
		actualUser = cinemaSystem.getUserAccountManager()
				.getAuthenticatedUser();

		// assert
		Assert.assertEquals(expectedUser, actualUser);
	}

	@Test
	public void testCinemaSystemUserAccountManagerChangePassword()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		User actualLocalUser = null;
		User actualDatabaseUser = null;
		String expectedPassword = createdUser.getPassword() + "123";
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		// act
		DAO<User> userDAO = new DbDAO<>(User.class);

		try {
			cinemaSystem.getUserAccountManager().changePassword(
					createdUser.getPassword(), expectedPassword);
		} catch (Exception e1) {
			Assert.fail("Change password operation failed");
		}

		createdUser.setPassword(expectedPassword);

		try {
			actualDatabaseUser = userDAO.read(createdUser.getId());
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read user operation failed");
		}

		actualLocalUser = cinemaSystem.getUserAccountManager()
				.getAuthenticatedUser();

		// assert
		Assert.assertEquals(createdUser, actualDatabaseUser);
		Assert.assertEquals(createdUser, actualLocalUser);
	}

	@Test
	public void testCinemaSystemUserAccountManagerChangePasswordInvalidCredentials()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		String passwordToChange = createdUser.getPassword() + "123";
		String invalidPassword = createdUser.getPassword() + "456";
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());
		boolean isExceptionThrown = false;

		// act
		try {
			cinemaSystem.getUserAccountManager().changePassword(
					invalidPassword, passwordToChange);
		} catch (InvalidCredentialsException e) {
			isExceptionThrown = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Change password operation failed");
		}

		// assert
		Assert.assertTrue(isExceptionThrown);

	}

	@Test
	public void testCinemaSystemMoviesManagerInvalidRoleOperations()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		Movie createdMovie = this.createTestMovie(null, null);
		Movie movieToCreate = new Movie(this.movieTitle, this.movieDescription,
				this.movieGenre, this.movieLength);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		boolean isAddExceptionThrown = false;
		boolean isEditExceptionThrown = false;
		boolean isDeleteExceptionThrown = false;
		boolean isGetAllOperationSuccesful = false;

		// act
		try {
			cinemaSystem.getMoviesManager().addManaged(movieToCreate);
		} catch (InvalidUserRoleException e) {
			isAddExceptionThrown = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add managed movie operation failed");
		}

		try {
			cinemaSystem.getMoviesManager().deleteManaged(createdMovie);
		} catch (InvalidUserRoleException e) {
			isDeleteExceptionThrown = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Delete managed movie operation failed");
		}

		createdMovie.setDescription(movieToCreate.getDescription() + "123");

		try {
			cinemaSystem.getMoviesManager().editManaged(createdMovie);
		} catch (InvalidUserRoleException e) {
			isEditExceptionThrown = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Edit managed movie operation failed");
		}

		try {
			cinemaSystem.getMoviesManager().getAllManaged();
			isGetAllOperationSuccesful = true;
		} catch (InvalidUserRoleException e) {
			Assert.fail("Invalid user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get all managed operation failed");
		}

		// assert
		Assert.assertTrue(isAddExceptionThrown);
		Assert.assertTrue(isDeleteExceptionThrown);
		Assert.assertTrue(isEditExceptionThrown);
		Assert.assertTrue(isGetAllOperationSuccesful);
	}

	@Test
	public void testCinemaSystemMoviesManager()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		Movie expectedMovie1 = this.createTestMovie(null, null);
		Movie expectedMovie2 = this.createTestMovie(null, null);
		Movie expectedMovie3 = this.createTestMovie(null, null);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		int expectedMoviesListLength = 3;
		int actualMoviesListLength = 0;

		boolean isMovie1Added = false;
		boolean isMovie2Added = false;
		boolean isMovie3Added = false;

		String editedTitle = "edytowany tytul";
		String editedDescription = "edytowany opis";
		Genre editedGenre = Genre.Horror;
		String editedLength = "3h 03min";

		Movie actualMovie1 = null;
		boolean isMovie2Deleted = false;

		// act
		DAO<Movie> movieDAO = new DbDAO<Movie>(Movie.class);

		List<Movie> allMoviesBeforeAdd = null;
		try {
			allMoviesBeforeAdd = cinemaSystem.getMoviesManager()
					.getAllManaged();
		} catch (InvalidUserRoleException e1) {
			Assert.fail("Invalid user role");
		} catch (FailedDatabaseOperationException e1) {
			Assert.fail("Get all managed movies operation failed");
		}

		try {
			expectedMovie1.setId(cinemaSystem.getMoviesManager().addManaged(
					expectedMovie1));
			expectedMovie2.setId(cinemaSystem.getMoviesManager().addManaged(
					expectedMovie2));
			expectedMovie3.setId(cinemaSystem.getMoviesManager().addManaged(
					expectedMovie3));
		} catch (InvalidUserRoleException e) {
			Assert.fail("Invalid user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add managed movie operation failed");
		}

		List<Movie> allMoviesAfterAdd = null;
		try {
			allMoviesAfterAdd = cinemaSystem.getMoviesManager().getAllManaged();
		} catch (InvalidUserRoleException e1) {
			Assert.fail("Invalid user role");
		} catch (FailedDatabaseOperationException e1) {
			Assert.fail("Get all managed movies operation failed");
		}

		allMoviesAfterAdd.removeAll(allMoviesBeforeAdd);
		actualMoviesListLength = allMoviesAfterAdd.size();

		if (allMoviesAfterAdd.contains(expectedMovie1)) {
			isMovie1Added = true;
		}

		if (allMoviesAfterAdd.contains(expectedMovie2)) {
			isMovie2Added = true;
		}

		if (allMoviesAfterAdd.contains(expectedMovie3)) {
			isMovie3Added = true;
		}

		expectedMovie1.setDescription(editedDescription);
		expectedMovie1.setGenre(editedGenre);
		expectedMovie1.setLength(editedLength);
		expectedMovie1.setTitle(editedTitle);

		try {
			cinemaSystem.getMoviesManager().editManaged(expectedMovie1);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Invalid user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Edit managed movie operation failed");
		}

		try {
			actualMovie1 = movieDAO.read(expectedMovie1.getId());
		} catch (FailedDatabaseOperationException e2) {
			Assert.fail("Read movie operation failed");
		}

		try {
			cinemaSystem.getMoviesManager().deleteManaged(expectedMovie2);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Invalid user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Delete managed movie operation failed");
		}

		try {
			movieDAO.read(expectedMovie2.getId());
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isMovie2Deleted = true;
			} else {
				Assert.fail("Read user operation failed");
			}
		}

		// assert
		Assert.assertEquals(expectedMoviesListLength, actualMoviesListLength);
		Assert.assertTrue(isMovie1Added);
		Assert.assertTrue(isMovie2Added);
		Assert.assertTrue(isMovie3Added);
		Assert.assertEquals(expectedMovie1, actualMovie1);
		Assert.assertTrue(isMovie2Deleted);
	}

	@Test
	public void testCinemaSystemDbShowFinderGetShowsByDate()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		Cinema otherCinema = this.createTestCinema();
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Show expectedShow = this.createTestShow(createdUser.getCinemaId(),
				null, null, null, 0, 0, 0);

		Show showWithNotMatchingCinema = this.createTestShow(
				otherCinema.getId(), null, null, null, 0, 0, 0);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(expectedShow.getDate());
		calendar.add(Calendar.HOUR, 1);

		Show expectedShowWithinSameDay = this.createTestShow(
				createdUser.getCinemaId(), null,
				new Date(calendar.getTimeInMillis()), null, 0, 0, 0);

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Show showWithNotMatchingDate = this.createTestShow(
				createdUser.getCinemaId(), null,
				new Date(calendar.getTimeInMillis()), null, 0, 0, 0);

		List<Show> allShows = null;
		int expectedListSize = 2;
		int actualListSize = 0;

		// act
		calendar.setTime(expectedShow.getDate());
		calendar.set(Calendar.HOUR, 10);

		allShows = cinemaSystem.getShowFinder().getShowsByDate(
				new Date(calendar.getTimeInMillis()));

		actualListSize = allShows.size();

		// assert
		Assert.assertEquals(expectedListSize, actualListSize);
		Assert.assertTrue(allShows.contains(expectedShow));
		Assert.assertTrue(allShows.contains(expectedShowWithinSameDay));
		Assert.assertFalse(allShows.contains(showWithNotMatchingCinema));
		Assert.assertFalse(allShows.contains(showWithNotMatchingDate));
	}

	@Test
	public void testCinemaSystemDbShowFinderGetShowsByTitle()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		String createdTitle1 = "Tytul Filmu";
		String createdTitle2 = "Drugi tytul";

		String searchIncompleteTitleWithUppercase = "Tytu";
		String searchCompleteTitleWithLowercase = "tytul filmu";
		String searchIncompleteTitleWithLowercase = "tytu";

		User createdUser = this.createTestUser(Role.Cashier);
		Cinema otherCinema = this.createTestCinema();
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Show createdShow1 = this.createTestShow(createdUser.getCinemaId(),
				createdTitle1, null, null, 0, 0, 0);

		Show createdShow2 = this.createTestShow(createdUser.getCinemaId(),
				createdTitle2, null, null, 0, 0, 0);

		Show showWithNotMatchingCinema = this.createTestShow(
				otherCinema.getId(), createdTitle1, null, null, 0, 0, 0);

		int expectedIncompleteTitleWithUppercaseSearchListSize = 2;
		int actualIncompleteTitleWithUppercaseSearchListSize = 0;

		int expectedCompleteTitleWithLowercaseSearchListSize = 1;
		int actualCompleteTitleWithLowercaseSearchListSize = 0;

		int expectedIncompleteTitleWithLowercaseSearchListSize = 2;
		int actualIncompleteTitleWithLowercaseSearchListSize = 0;

		List<Show> incompleteTitleWithUppercaseSearchList = null;
		List<Show> completeTitleWithLowercaseSearchList = null;
		List<Show> incompleteTitleWithLowercaseSearchList = null;

		// act
		incompleteTitleWithUppercaseSearchList = cinemaSystem.getShowFinder()
				.getShowsByTitle(searchIncompleteTitleWithUppercase);

		actualIncompleteTitleWithUppercaseSearchListSize = incompleteTitleWithUppercaseSearchList
				.size();

		completeTitleWithLowercaseSearchList = cinemaSystem.getShowFinder()
				.getShowsByTitle(searchCompleteTitleWithLowercase);

		actualCompleteTitleWithLowercaseSearchListSize = completeTitleWithLowercaseSearchList
				.size();

		incompleteTitleWithLowercaseSearchList = cinemaSystem.getShowFinder()
				.getShowsByTitle(searchIncompleteTitleWithLowercase);

		actualIncompleteTitleWithLowercaseSearchListSize = incompleteTitleWithLowercaseSearchList
				.size();

		// assert
		Assert.assertEquals(expectedIncompleteTitleWithLowercaseSearchListSize,
				actualIncompleteTitleWithLowercaseSearchListSize);
		Assert.assertEquals(expectedCompleteTitleWithLowercaseSearchListSize,
				actualCompleteTitleWithLowercaseSearchListSize);
		Assert.assertEquals(expectedIncompleteTitleWithUppercaseSearchListSize,
				actualIncompleteTitleWithUppercaseSearchListSize);

		Assert.assertTrue(incompleteTitleWithUppercaseSearchList
				.contains(createdShow1));
		Assert.assertTrue(incompleteTitleWithUppercaseSearchList
				.contains(createdShow2));
		Assert.assertFalse(incompleteTitleWithUppercaseSearchList
				.contains(showWithNotMatchingCinema));

		Assert.assertTrue(completeTitleWithLowercaseSearchList
				.contains(createdShow1));
		Assert.assertFalse(completeTitleWithLowercaseSearchList
				.contains(showWithNotMatchingCinema));

		Assert.assertTrue(incompleteTitleWithLowercaseSearchList
				.contains(createdShow1));
		Assert.assertTrue(incompleteTitleWithLowercaseSearchList
				.contains(createdShow2));
		Assert.assertFalse(incompleteTitleWithLowercaseSearchList
				.contains(showWithNotMatchingCinema));
	}

	@Test
	public void testCinemaSystemDbShowFinderGetShowsByTime()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		Cinema otherCinema = this.createTestCinema();
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Show expectedShow = this.createTestShow(createdUser.getCinemaId(),
				null, null, null, 0, 0, 0);

		Show showWithNotMatchingCinema = this.createTestShow(
				otherCinema.getId(), null, null, null, 0, 0, 0);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(expectedShow.getDate());

		calendar.add(Calendar.HOUR, 1);
		Show expectedShowWithAfterDate = this.createTestShow(
				createdUser.getCinemaId(), null,
				new Date(calendar.getTimeInMillis()), null, 0, 0, 0);

		calendar.add(Calendar.HOUR, -2);
		Show expectedShowWithBeforeDate = this.createTestShow(
				createdUser.getCinemaId(), null,
				new Date(calendar.getTimeInMillis()), null, 0, 0, 0);

		calendar.add(Calendar.HOUR_OF_DAY, 4);
		Show showWithNotMatchingAfterDate = this.createTestShow(
				createdUser.getCinemaId(), null,
				new Date(calendar.getTimeInMillis()), null, 0, 0, 0);

		calendar.add(Calendar.HOUR_OF_DAY, -6);
		Show showWithNotMatchingBeforeDate = this.createTestShow(
				createdUser.getCinemaId(), null,
				new Date(calendar.getTimeInMillis()), null, 0, 0, 0);

		int hourInterval = 2;
		List<Show> actualShowsList = null;
		int expectedListSize = 3;
		int actualListSize = 0;

		// act
		calendar.setTime(expectedShow.getDate());

		actualShowsList = cinemaSystem.getShowFinder().getShowsByTime(
				new Date(calendar.getTimeInMillis()), hourInterval);

		actualListSize = actualShowsList.size();

		// assert
		Assert.assertEquals(expectedListSize, actualListSize);
		Assert.assertTrue(actualShowsList.contains(expectedShow));
		Assert.assertTrue(actualShowsList.contains(expectedShowWithAfterDate));
		Assert.assertTrue(actualShowsList.contains(expectedShowWithBeforeDate));
		Assert.assertFalse(actualShowsList.contains(showWithNotMatchingCinema));
		Assert.assertFalse(actualShowsList
				.contains(showWithNotMatchingAfterDate));
		Assert.assertFalse(actualShowsList
				.contains(showWithNotMatchingBeforeDate));
	}

	@Test
	public void testCinemaSystemDbShowFinderGetShowsByGenre()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		Genre searchedGenre = Genre.Comedy;
		Genre otherGenre = Genre.Horror;

		User createdUser = this.createTestUser(Role.Cashier);
		Cinema otherCinema = this.createTestCinema();
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Show createdShowWithSearchedGenre1 = this.createTestShow(
				createdUser.getCinemaId(), null, null, searchedGenre, 0, 0, 0);

		Show createdShowWithSearchedGenre2 = this.createTestShow(
				createdUser.getCinemaId(), null, null, searchedGenre, 0, 0, 0);

		Show createdShowWithOtherCinema = this.createTestShow(
				otherCinema.getId(), null, null, searchedGenre, 0, 0, 0);

		Show createdShowWithOtherGenre = this.createTestShow(
				createdUser.getCinemaId(), null, null, otherGenre, 0, 0, 0);

		List<Show> actualShowsList = null;
		int expectedShowsListSize = 2;
		int actualShowsListSize = 0;

		// act
		try {
			actualShowsList = cinemaSystem.getShowFinder().getShowsByGenre(
					searchedGenre);
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get shows by title operation failed");
		}

		actualShowsListSize = actualShowsList.size();

		// assert
		Assert.assertEquals(expectedShowsListSize, actualShowsListSize);
		Assert.assertTrue(actualShowsList
				.contains(createdShowWithSearchedGenre1));
		Assert.assertTrue(actualShowsList
				.contains(createdShowWithSearchedGenre2));
		Assert.assertFalse(actualShowsList.contains(createdShowWithOtherGenre));
		Assert.assertFalse(actualShowsList.contains(createdShowWithOtherCinema));
	}

	@Test
	public void testCinemaSystemAdministrativeModuleCinemasManagerCreateAndRead()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema expectedCinema = new Cinema(this.cinemaName, this.cinemaCity);
		Cinema actualCinema = null;

		// act
		try {
			expectedCinema.setId(cinemaSystem.getAdministrativeModule()
					.getCinemasManager().create(expectedCinema));
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Create cinema operation failed");
		}

		try {
			actualCinema = cinemaSystem.getAdministrativeModule()
					.getCinemasManager().read(expectedCinema.getId());
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Read cinema operation failed");
		}

		// assert
		Assert.assertEquals(expectedCinema, actualCinema);
	}

	@Test
	public void testCinemaSystemCinemaManagerModuleAuditoriumsManagerAddAndGetAll()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());
		Cinema otherCinema = this.createTestCinema();

		Auditorium createdAuditorium = new Auditorium(
				createdUser.getCinemaId(), this.auditoriumName);

		Auditorium auditoriumWithOtherCinema = new Auditorium(
				otherCinema.getId(), this.auditoriumName);

		boolean isExceptionThrown = false;
		boolean isAuditoriumBeforeAdd = false;
		boolean isAuditoriumAfterAdd = false;

		// act
		List<Auditorium> auditoriums = null;
		try {
			auditoriums = cinemaSystem.getCinemaManagerModule()
					.getAuditoriumsManager().getAllManaged();
		} catch (InvalidUserRoleException e1) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e1) {
			Assert.fail("Add auditorium operation failed");
		}

		isAuditoriumBeforeAdd = auditoriums.contains(createdAuditorium);

		try {
			cinemaSystem.getCinemaManagerModule().getAuditoriumsManager()
					.addManaged(auditoriumWithOtherCinema);
		} catch (InvalidUserRoleException e) {
			isExceptionThrown = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add auditorium operation failed");
		}

		try {
			createdAuditorium.setId(cinemaSystem.getCinemaManagerModule()
					.getAuditoriumsManager().addManaged(createdAuditorium));
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add auditorium operation failed");
		}

		List<Auditorium> auditoriumsAfterAdd = null;
		try {
			auditoriumsAfterAdd = cinemaSystem.getCinemaManagerModule()
					.getAuditoriumsManager().getAllManaged();
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add auditorium operation failed");
		}

		isAuditoriumAfterAdd = auditoriumsAfterAdd.contains(createdAuditorium);

		// assert
		Assert.assertFalse(isAuditoriumBeforeAdd);
		Assert.assertTrue(isAuditoriumAfterAdd);
		Assert.assertTrue(isExceptionThrown);
	}

	@Test
	public void testCinemaSystemAdministrativeModuleAuditoriumsManagerEditAndDelete()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		Cinema createdCinema1 = this.createTestCinema();
		Cinema createdCinema2 = this.createTestCinema();
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Auditorium createdAuditorium = new Auditorium(createdCinema1.getId(),
				this.auditoriumName);

		Auditorium editedAuditorium = new Auditorium(createdCinema2.getId(),
				"nowa nazwa");

		boolean isAuditoriumAfterEdit = false;
		boolean isAuditoriumAfterDelete = false;

		// act
		try {
			editedAuditorium.setId(cinemaSystem.getAdministrativeModule()
					.getAuditoriumsManager().addManaged(createdAuditorium));
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add auditorium operation failed");
		}

		try {
			cinemaSystem.getAdministrativeModule().getAuditoriumsManager()
					.editManaged(editedAuditorium);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Edit auditorium operation failed");
		}

		List<Auditorium> auditoriumsAfterEdit = null;
		try {
			auditoriumsAfterEdit = cinemaSystem.getAdministrativeModule()
					.getAuditoriumsManager().getAllManaged();
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get all auditoriums operation failed");
		}

		isAuditoriumAfterEdit = auditoriumsAfterEdit.contains(editedAuditorium);

		try {
			cinemaSystem.getAdministrativeModule().getAuditoriumsManager()
					.deleteManaged(editedAuditorium);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Delete auditorium operation failed");
		}

		List<Auditorium> auditoriumsAfterDelete = null;
		try {
			auditoriumsAfterDelete = cinemaSystem.getAdministrativeModule()
					.getAuditoriumsManager().getAllManaged();
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get all auditoriums operation failed");
		}

		isAuditoriumAfterDelete = auditoriumsAfterDelete
				.contains(editedAuditorium);

		// assert
		Assert.assertTrue(isAuditoriumAfterEdit);
		Assert.assertFalse(isAuditoriumAfterDelete);
	}

	@Test
	public void testCinemaSystemCinemaManagerModuleAuditoriumsManagerEditAndDelete()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Auditorium createdAuditorium = new Auditorium(
				createdUser.getCinemaId(), this.auditoriumName);

		Auditorium editedAuditorium = new Auditorium(createdUser.getCinemaId(),
				"nowa nazwa");

		boolean isAuditoriumAfterEdit = false;
		boolean isAuditoriumAfterDelete = false;

		// act
		try {
			editedAuditorium.setId(cinemaSystem.getCinemaManagerModule()
					.getAuditoriumsManager().addManaged(createdAuditorium));
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add auditorium operation failed");
		}

		try {
			cinemaSystem.getCinemaManagerModule().getAuditoriumsManager()
					.editManaged(editedAuditorium);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Edit auditorium operation failed");
		}

		List<Auditorium> auditoriumsAfterEdit = null;
		try {
			auditoriumsAfterEdit = cinemaSystem.getCinemaManagerModule()
					.getAuditoriumsManager().getAllManaged();
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get all auditoriums operation failed");
		}

		isAuditoriumAfterEdit = auditoriumsAfterEdit.contains(editedAuditorium);

		try {
			cinemaSystem.getCinemaManagerModule().getAuditoriumsManager()
					.deleteManaged(editedAuditorium);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Delete auditorium operation failed");
		}

		List<Auditorium> auditoriumsAfterDelete = null;
		try {
			auditoriumsAfterDelete = cinemaSystem.getCinemaManagerModule()
					.getAuditoriumsManager().getAllManaged();
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get all auditoriums operation failed");
		}

		isAuditoriumAfterDelete = auditoriumsAfterDelete
				.contains(editedAuditorium);

		// assert
		Assert.assertTrue(isAuditoriumAfterEdit);
		Assert.assertFalse(isAuditoriumAfterDelete);
	}

	@Test
	public void testCinemaSystemCinemaManagerModuleShowsManagerAddAndGetAll()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());
		Cinema otherCinema = this.createTestCinema();

		Show createdShow = this.createTestShow(createdUser.getCinemaId(), null,
				null, null, 0, 0, 0);
		Show createdShowWithOtherCinema = this.createTestShow(
				otherCinema.getId(), null, null, null, 0, 0, 0);

		boolean isCreatedShow = false;
		boolean isExceptionThrown = false;

		// act
		createdShow.setId(cinemaSystem.getCinemaManagerModule()
				.getShowsManager().addManaged(createdShow));
		try {
			createdShowWithOtherCinema.setId(cinemaSystem
					.getCinemaManagerModule().getShowsManager()
					.addManaged(createdShowWithOtherCinema));
		} catch (InvalidUserRoleException e) {
			isExceptionThrown = true;
		}

		List<Show> allShows = null;
		allShows = cinemaSystem.getCinemaManagerModule().getShowsManager()
				.getAllManaged();
		isCreatedShow = allShows.contains(createdShow);

		// assert
		Assert.assertTrue(isExceptionThrown);
		Assert.assertTrue(isCreatedShow);
	}

	@Test
	public void testCinemaSystemCinemaManagerModuleShowsManagerEditAndDelete()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Movie createdMovie = this.createTestMovie(null, null);
		Movie editedMovie = this.createTestMovie(null, null);
		Cinema otherCinema = this.createTestCinema();
		Auditorium auditoriumWithCreatedUserCinema = this
				.createTestAuditorium(createdUser.getCinemaId());
		Auditorium auditoriumWithOtherCinema = this
				.createTestAuditorium(otherCinema.getId());

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);

		Show createdShow = new Show(auditoriumWithCreatedUserCinema.getId(),
				createdMovie.getId(), new Date(calendar.getTimeInMillis()),
				this.showPrice);

		Show editedShowWithOtherCinema = new Show(
				auditoriumWithOtherCinema.getId(), createdMovie.getId(),
				new Date(calendar.getTimeInMillis()), this.showPrice);

		Show editedShowWithUserCinema = new Show(
				auditoriumWithCreatedUserCinema.getId(), editedMovie.getId(),
				new Date(calendar.getTimeInMillis()), this.showPrice + 5.50);

		boolean isShowAfterEdit = false;
		boolean isShowAfterDelete = false;
		boolean isExceptionThrown = false;

		// act
		int createdShowId = 0;
		try {
			createdShowId = cinemaSystem.getCinemaManagerModule()
					.getShowsManager().addManaged(createdShow);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add show operation failed");
		}

		editedShowWithOtherCinema.setId(createdShowId);
		editedShowWithUserCinema.setId(createdShowId);

		try {
			cinemaSystem.getCinemaManagerModule().getShowsManager()
					.editManaged(editedShowWithOtherCinema);
		} catch (InvalidUserRoleException e) {
			isExceptionThrown = true;
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Edit show operation failed");
		}

		try {
			cinemaSystem.getCinemaManagerModule().getShowsManager()
					.editManaged(editedShowWithUserCinema);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Edit show operation failed");
		}

		List<Show> showsAfterEdit = null;
		try {
			showsAfterEdit = cinemaSystem.getCinemaManagerModule()
					.getShowsManager().getAllManaged();
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get all shows operation failed");
		}

		isShowAfterEdit = showsAfterEdit.contains(editedShowWithUserCinema);

		try {
			cinemaSystem.getCinemaManagerModule().getShowsManager()
					.deleteManaged(editedShowWithUserCinema);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Delete auditorium operation failed");
		}

		List<Show> showsAfterDelete = null;
		try {
			showsAfterDelete = cinemaSystem.getCinemaManagerModule()
					.getShowsManager().getAllManaged();
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get all shows operation failed");
		}

		isShowAfterDelete = showsAfterDelete.contains(editedShowWithUserCinema);

		// assert
		Assert.assertTrue(isShowAfterEdit);
		Assert.assertTrue(isExceptionThrown);
		Assert.assertFalse(isShowAfterDelete);
	}

	@Test
	public void testCinemaSystemAdministrativeModuleShowsManagerEditAndDelete()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Movie createdMovie = this.createTestMovie(null, null);
		Movie editedMovie = this.createTestMovie(null, null);
		Auditorium createdAuditorium = this.createTestAuditorium(0);
		Auditorium editedAuditorium = this.createTestAuditorium(0);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);

		Show createdShow = new Show(createdAuditorium.getId(),
				createdMovie.getId(), new Date(calendar.getTimeInMillis()),
				this.showPrice);

		Show editedShow = new Show(editedAuditorium.getId(),
				editedMovie.getId(), new Date(calendar.getTimeInMillis()),
				this.showPrice + 5.50);

		boolean isShowAfterEdit = false;
		boolean isShowAfterDelete = false;

		// act
		try {
			editedShow.setId(cinemaSystem.getAdministrativeModule()
					.getShowsManager().addManaged(createdShow));
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Add show operation failed");
		}

		try {
			cinemaSystem.getAdministrativeModule().getShowsManager()
					.editManaged(editedShow);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Edit show operation failed");
		}

		List<Show> showsAfterEdit = null;
		try {
			showsAfterEdit = cinemaSystem.getAdministrativeModule()
					.getShowsManager().getAllManaged();
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get all shows operation failed");
		}

		isShowAfterEdit = showsAfterEdit.contains(editedShow);

		try {
			cinemaSystem.getAdministrativeModule().getShowsManager()
					.deleteManaged(editedShow);
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Delete auditorium operation failed");
		}

		List<Show> showsAfterDelete = null;
		try {
			showsAfterDelete = cinemaSystem.getAdministrativeModule()
					.getShowsManager().getAllManaged();
		} catch (InvalidUserRoleException e) {
			Assert.fail("Not matching user role");
		} catch (FailedDatabaseOperationException e) {
			Assert.fail("Get all shows operation failed");
		}

		isShowAfterDelete = showsAfterDelete.contains(editedShow);

		// assert
		Assert.assertTrue(isShowAfterEdit);
		Assert.assertFalse(isShowAfterDelete);
	}

	@Test
	public void testCinemaSystemCinemaManagerModuleSeatsManagerAddAndGetAll()
			throws InvalidUserRoleException, FailedDatabaseOperationException,
			InvalidCredentialsException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());
		Cinema otherCinema = this.createTestCinema();
		Auditorium createdAuditorium = this.createTestAuditorium(createdUser
				.getCinemaId());
		Auditorium otherAuditorium = this.createTestAuditorium(otherCinema
				.getId());

		Seat createdSeat = new Seat(createdAuditorium.getId(), 1);
		Seat seatInOtherCinema = new Seat(otherAuditorium.getId(), 1);

		boolean isSeatAfterAdd = false;
		boolean isExceptionThrownForOtherCinema = false;

		// act
		try {
			cinemaSystem.getCinemaManagerModule().getSeatsManager()
					.addManaged(seatInOtherCinema);
		} catch (InvalidUserRoleException e) {
			isExceptionThrownForOtherCinema = true;
		}

		createdSeat.setId(cinemaSystem.getCinemaManagerModule()
				.getSeatsManager().addManaged(createdSeat));

		List<Seat> seatsAfterAdd = cinemaSystem.getCinemaManagerModule()
				.getSeatsManager().getAllManaged();

		isSeatAfterAdd = seatsAfterAdd.contains(createdSeat);

		// assert
		Assert.assertTrue(isSeatAfterAdd);
		Assert.assertTrue(isExceptionThrownForOtherCinema);
	}

	@Test
	public void testCinemaSystemAdministratorModuleSeatsManagerAddAndGetAll()
			throws InvalidUserRoleException, FailedDatabaseOperationException,
			InvalidCredentialsException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Auditorium createdAuditorium = this.createTestAuditorium(0);

		Seat createdSeat = new Seat(createdAuditorium.getId(), 1);

		boolean isSeatAfterAdd = false;

		// act
		createdSeat.setId(cinemaSystem.getAdministrativeModule()
				.getSeatsManager().addManaged(createdSeat));

		List<Seat> seatsAfterAdd = cinemaSystem.getAdministrativeModule()
				.getSeatsManager().getAllManaged();

		isSeatAfterAdd = seatsAfterAdd.contains(createdSeat);

		// assert
		Assert.assertTrue(isSeatAfterAdd);
	}

	@Test
	public void testCinemaSystemCinemaManagerModuleSeatsManagerEditAndDelete()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Auditorium otherAuditorium = this.createTestAuditorium(createdUser
				.getCinemaId());

		Seat createdSeat = this.createTestSeat(1, 0);
		Seat editedSeat = new Seat(createdSeat.getId(),
				otherAuditorium.getId(), 2);

		Seat actualSeat = null;
		boolean isExceptionThrownAfterDelete = false;

		// act
		cinemaSystem.getCinemaManagerModule().getSeatsManager()
				.editManaged(editedSeat);

		DAO<Seat> seatDAO = new DbDAO<>(Seat.class);
		actualSeat = seatDAO.read(editedSeat.getId());

		cinemaSystem.getCinemaManagerModule().getSeatsManager()
				.deleteManaged(editedSeat);

		try {
			seatDAO.read(editedSeat.getId());
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrownAfterDelete = true;
			} else {
				throw e;
			}
		}

		// assert
		Assert.assertEquals(editedSeat, actualSeat);
		Assert.assertTrue(isExceptionThrownAfterDelete);
	}

	@Test
	public void testCinemaSystemAdministratorModuleSeatsManagerEditAndDelete()
			throws InvalidUserRoleException, FailedDatabaseOperationException,
			InvalidCredentialsException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Auditorium otherAuditorium = this.createTestAuditorium(0);

		Seat createdSeat = this.createTestSeat(1, 0);
		Seat editedSeat = new Seat(createdSeat.getId(),
				otherAuditorium.getId(), 2);

		Seat actualSeat = null;
		boolean isExceptionThrownAfterDelete = false;

		// act
		cinemaSystem.getAdministrativeModule().getSeatsManager()
				.editManaged(editedSeat);

		DAO<Seat> seatDAO = new DbDAO<>(Seat.class);
		actualSeat = seatDAO.read(editedSeat.getId());

		cinemaSystem.getAdministrativeModule().getSeatsManager()
				.deleteManaged(editedSeat);

		try {
			seatDAO.read(editedSeat.getId());
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrownAfterDelete = true;
			} else {
				throw e;
			}
		}

		// assert
		Assert.assertEquals(editedSeat, actualSeat);
		Assert.assertTrue(isExceptionThrownAfterDelete);

	}

	@Test
	public void testCinemaSystemCinemaManagerModuleUsersManagerAddAndGetAll()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		User cashierToCreate = new User(createdUser.getCinemaId(),
				this.userName, this.userLastName, this.userPhoneNumber,
				this.userAddress, Role.Cashier, UUID.randomUUID().toString()
						.substring(0, 8), this.userPassword);

		User cinemaManagerToCreate = new User(createdUser.getCinemaId(),
				this.userName, this.userLastName, this.userPhoneNumber,
				this.userAddress, Role.CinemaManager, UUID.randomUUID()
						.toString().substring(0, 8), this.userPassword);

		User administratorToCreate = new User(createdUser.getCinemaId(),
				this.userName, this.userLastName, this.userPhoneNumber,
				this.userAddress, Role.Administrator, UUID.randomUUID()
						.toString().substring(0, 8), this.userPassword);

		boolean isCashierCreated = false;
		boolean isCinemaManagerCreated = false;
		boolean isExceptionThrownWhileAddingAdministrator = false;

		// act
		cashierToCreate.setId(cinemaSystem.getCinemaManagerModule()
				.getUsersManager().addManaged(cashierToCreate));

		cinemaManagerToCreate.setId(cinemaSystem.getCinemaManagerModule()
				.getUsersManager().addManaged(cinemaManagerToCreate));

		try {
			cinemaSystem.getCinemaManagerModule().getUsersManager()
					.addManaged(administratorToCreate);
		} catch (InvalidUserRoleException e) {
			isExceptionThrownWhileAddingAdministrator = true;
		}

		isCashierCreated = cinemaSystem.getCinemaManagerModule()
				.getUsersManager().getAllManaged().contains(cashierToCreate);
		isCinemaManagerCreated = cinemaSystem.getCinemaManagerModule()
				.getUsersManager().getAllManaged()
				.contains(cinemaManagerToCreate);

		// assert
		Assert.assertTrue(isExceptionThrownWhileAddingAdministrator);
		Assert.assertTrue(isCashierCreated);
		Assert.assertTrue(isCinemaManagerCreated);

	}

	@Test
	public void testCinemaSystemAdministrativeModuleUsersManagerAddAndGetAll()
			throws InvalidUserRoleException, FailedDatabaseOperationException,
			InvalidCredentialsException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());
		Cinema createdCinema = this.createTestCinema();

		User cashierToCreate = new User(createdCinema.getId(), this.userName,
				this.userLastName, this.userPhoneNumber, this.userAddress,
				Role.Cashier, UUID.randomUUID().toString().substring(0, 8),
				this.userPassword);

		User cinemaManagerToCreate = new User(createdCinema.getId(),
				this.userName, this.userLastName, this.userPhoneNumber,
				this.userAddress, Role.CinemaManager, UUID.randomUUID()
						.toString().substring(0, 8), this.userPassword);

		User administratorToCreate = new User(null, this.userName,
				this.userLastName, this.userPhoneNumber, this.userAddress,
				Role.Administrator, UUID.randomUUID().toString()
						.substring(0, 8), this.userPassword);

		boolean isCashierCreated = false;
		boolean isCinemaManagerCreated = false;
		boolean isAdministratorCreated = false;

		// act
		cashierToCreate.setId(cinemaSystem.getAdministrativeModule()
				.getUsersManager().addManaged(cashierToCreate));

		cinemaManagerToCreate.setId(cinemaSystem.getAdministrativeModule()
				.getUsersManager().addManaged(cinemaManagerToCreate));

		administratorToCreate.setCinemaId(0);
		administratorToCreate.setId(cinemaSystem.getAdministrativeModule()
				.getUsersManager().addManaged(administratorToCreate));
		administratorToCreate.setCinemaId(null);

		isCashierCreated = cinemaSystem.getAdministrativeModule()
				.getUsersManager().getAllManaged().contains(cashierToCreate);

		isCinemaManagerCreated = cinemaSystem.getAdministrativeModule()
				.getUsersManager().getAllManaged()
				.contains(cinemaManagerToCreate);

		isAdministratorCreated = cinemaSystem.getAdministrativeModule()
				.getUsersManager().getAllManaged()
				.contains(administratorToCreate);

		// assert
		Assert.assertTrue(isCashierCreated);
		Assert.assertTrue(isCinemaManagerCreated);
		Assert.assertTrue(isAdministratorCreated);
	}

	@Test
	public void testCinemaSystemCinemaManagerModuleUsersManagerEditAndDelete()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());
		Cinema otherCinema = this.createTestCinema();

		User createdCashier = this.createTestUser(Role.Cashier,
				createdUser.getCinemaId());
		User createdCinemaManager = this.createTestUser(Role.CinemaManager,
				createdUser.getCinemaId());
		User createdAdministrator = this.createTestUser(Role.Administrator);

		User cashierToEdit = new User(createdCashier.getId(),
				createdUser.getCinemaId(), "edytowane imie",
				"edytowane nazwisko", "+42 665 884 214", "edytowany adres",
				Role.Cashier, UUID.randomUUID().toString().substring(0, 8),
				"edytowane haslo");

		User cinemaManagerToEdit = new User(createdCinemaManager.getId(),
				createdUser.getCinemaId(), "edytowane imie",
				"edytowane nazwisko", "+42 665 884 214", "edytowany adres",
				Role.CinemaManager, UUID.randomUUID().toString()
						.substring(0, 8), "edytowane haslo");

		User administratorToEdit = new User(createdAdministrator.getId(),
				createdUser.getCinemaId(), "edytowane imie",
				"edytowane nazwisko", "+42 665 884 214", "edytowany adres",
				Role.Administrator, UUID.randomUUID().toString()
						.substring(0, 8), "edytowane haslo");

		User cashierToEditWithOtherCinema = new User(createdCashier.getId(),
				otherCinema.getId(), "edytowane imie", "edytowane nazwisko",
				"+42 665 884 214", "edytowany adres", Role.Cashier, UUID
						.randomUUID().toString().substring(0, 8),
				"edytowane haslo");

		User cashierToEditChangeToAdministrator = new User(
				createdCashier.getId(), createdUser.getCinemaId(),
				"edytowane imie", "edytowane nazwisko", "+42 665 884 214",
				"edytowany adres", Role.Administrator, UUID.randomUUID()
						.toString().substring(0, 8), "edytowane haslo");

		boolean isCashierEdited = false;
		boolean isCinemaManagerEdited = false;
		boolean isExceptionThrownWhileEditingAdministrator = false;
		boolean isExceptionThrownWhileEditingCashierWithOtherCinema = false;
		boolean isExceptionThrownWhileEditingCashierChangeToAdministrator = false;

		// act
		cinemaSystem.getCinemaManagerModule().getUsersManager()
				.editManaged(cashierToEdit);

		cinemaSystem.getCinemaManagerModule().getUsersManager()
				.editManaged(cinemaManagerToEdit);

		try {
			cinemaSystem.getCinemaManagerModule().getUsersManager()
					.editManaged(administratorToEdit);
		} catch (InvalidUserRoleException e) {
			isExceptionThrownWhileEditingAdministrator = true;
		}

		try {
			cinemaSystem.getCinemaManagerModule().getUsersManager()
					.editManaged(cashierToEditWithOtherCinema);
		} catch (InvalidUserRoleException e) {
			isExceptionThrownWhileEditingCashierWithOtherCinema = true;
		}

		try {
			cinemaSystem.getCinemaManagerModule().getUsersManager()
					.editManaged(cashierToEditChangeToAdministrator);
		} catch (InvalidUserRoleException e) {
			isExceptionThrownWhileEditingCashierChangeToAdministrator = true;
		}

		isCashierEdited = cinemaSystem.getCinemaManagerModule()
				.getUsersManager().getAllManaged().contains(cashierToEdit);

		isCinemaManagerEdited = cinemaSystem.getCinemaManagerModule()
				.getUsersManager().getAllManaged()
				.contains(cinemaManagerToEdit);

		// assert
		Assert.assertTrue(isCashierEdited);
		Assert.assertTrue(isCinemaManagerEdited);
		Assert.assertTrue(isExceptionThrownWhileEditingAdministrator);
		Assert.assertTrue(isExceptionThrownWhileEditingCashierWithOtherCinema);
		Assert.assertTrue(isExceptionThrownWhileEditingCashierChangeToAdministrator);
	}

	@Test
	public void testCinemaSystemAdministrativeModuleUsersManagerEditAndDelete()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());
		Cinema createdCinema = this.createTestCinema();
		Cinema otherCinema = this.createTestCinema();

		User createdCashier = this.createTestUser(Role.Cashier,
				createdCinema.getId());
		User createdCinemaManager = this.createTestUser(Role.CinemaManager,
				createdCinema.getId());
		User createdAdministrator = this.createTestUser(Role.Administrator);
		User createdCashier2 = this.createTestUser(Role.Cashier,
				createdCinema.getId());
		User createdCashier3 = this.createTestUser(Role.Cashier,
				createdCinema.getId());

		User cashierToEdit = new User(createdCashier.getId(),
				createdCinema.getId(), "edytowane imie", "edytowane nazwisko",
				"+42 665 884 214", "edytowany adres", Role.Cashier, UUID
						.randomUUID().toString().substring(0, 8),
				"edytowane haslo");

		User cinemaManagerToEdit = new User(createdCinemaManager.getId(),
				createdCinema.getId(), "edytowane imie", "edytowane nazwisko",
				"+42 665 884 214", "edytowany adres", Role.CinemaManager, UUID
						.randomUUID().toString().substring(0, 8),
				"edytowane haslo");

		User administratorToEdit = new User(createdAdministrator.getId(), null,
				"edytowane imie", "edytowane nazwisko", "+42 665 884 214",
				"edytowany adres", Role.Administrator, UUID.randomUUID()
						.toString().substring(0, 8), "edytowane haslo");

		User cashierToEditWithOtherCinema = new User(createdCashier2.getId(),
				otherCinema.getId(), "edytowane imie", "edytowane nazwisko",
				"+42 665 884 214", "edytowany adres", Role.Cashier, UUID
						.randomUUID().toString().substring(0, 8),
				"edytowane haslo");

		User cashierToEditChangeToAdministrator = new User(
				createdCashier3.getId(), createdCinema.getId(),
				"edytowane imie", "edytowane nazwisko", "+42 665 884 214",
				"edytowany adres", Role.Administrator, UUID.randomUUID()
						.toString().substring(0, 8), "edytowane haslo");

		boolean isCashierEdited = false;
		boolean isCinemaManagerEdited = false;
		boolean isAdministratorEdited = false;
		boolean isCashierWithOtherCinemaEdited = false;
		boolean isCashierChangeToAdministratorEdited = false;

		// act
		cinemaSystem.getAdministrativeModule().getUsersManager()
				.editManaged(cashierToEdit);

		cinemaSystem.getAdministrativeModule().getUsersManager()
				.editManaged(cinemaManagerToEdit);

		administratorToEdit.setCinemaId(0);
		cinemaSystem.getAdministrativeModule().getUsersManager()
				.editManaged(administratorToEdit);
		administratorToEdit.setCinemaId(null);

		cinemaSystem.getAdministrativeModule().getUsersManager()
				.editManaged(cashierToEditWithOtherCinema);

		cinemaSystem.getAdministrativeModule().getUsersManager()
				.editManaged(cashierToEditChangeToAdministrator);

		isCashierEdited = cinemaSystem.getAdministrativeModule()
				.getUsersManager().getAllManaged().contains(cashierToEdit);

		isCinemaManagerEdited = cinemaSystem.getAdministrativeModule()
				.getUsersManager().getAllManaged()
				.contains(cinemaManagerToEdit);

		isAdministratorEdited = cinemaSystem.getAdministrativeModule()
				.getUsersManager().getAllManaged()
				.contains(administratorToEdit);

		isCashierWithOtherCinemaEdited = cinemaSystem.getAdministrativeModule()
				.getUsersManager().getAllManaged()
				.contains(cashierToEditWithOtherCinema);

		isCashierChangeToAdministratorEdited = cinemaSystem
				.getAdministrativeModule().getUsersManager().getAllManaged()
				.contains(cashierToEditChangeToAdministrator);

		// assert
		Assert.assertTrue(isCashierEdited);
		Assert.assertTrue(isCinemaManagerEdited);
		Assert.assertTrue(isAdministratorEdited);
		Assert.assertTrue(isCashierWithOtherCinemaEdited);
		Assert.assertTrue(isCashierChangeToAdministratorEdited);
	}

	@Test
	public void testCinemaStatisticsGetSoldTicketsByMovie()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());
		Movie createdMovie = this.createTestMovie(null, null);
		Movie otherMovie = this.createTestMovie(null, null);
		Cinema otherCinema = this.createTestCinema();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 15);
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DAY_OF_MONTH, 4);
		Date endDate = new Date(calendar.getTimeInMillis());

		calendar.add(Calendar.DAY_OF_MONTH, -2);
		this.createTestTicket(createdUser.getCinemaId(), 1,
				createdMovie.getId(), 0, 0,
				new Date(calendar.getTimeInMillis()), 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 2, otherMovie.getId(),
				0, 0, new Date(calendar.getTimeInMillis()), 0, 0);

		this.createTestTicket(otherCinema.getId(), 3, createdMovie.getId(), 0,
				0, new Date(calendar.getTimeInMillis()), 0, 0);

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		this.createTestTicket(createdUser.getCinemaId(), 4,
				createdMovie.getId(), 0, 0,
				new Date(calendar.getTimeInMillis()), 0, 0);

		calendar.add(Calendar.DAY_OF_MONTH, 2);
		this.createTestTicket(createdUser.getCinemaId(), 5,
				createdMovie.getId(), 0, 0,
				new Date(calendar.getTimeInMillis()), 0, 0);

		calendar.add(Calendar.DAY_OF_MONTH, 2);
		this.createTestTicket(createdUser.getCinemaId(), 6,
				createdMovie.getId(), 0, 0,
				new Date(calendar.getTimeInMillis()), 0, 0);

		calendar.add(Calendar.DAY_OF_MONTH, -6);
		this.createTestTicket(createdUser.getCinemaId(), 7,
				createdMovie.getId(), 0, 0,
				new Date(calendar.getTimeInMillis()), 0, 0);

		int expectedStatisticListSize = 3;
		int actualStatisticsListSize = 0;

		// act
		actualStatisticsListSize = cinemaSystem.getCinemaManagerModule()
				.getManagerStatistics()
				.getSoldTicketsByMovie(startDate, endDate, createdMovie);

		// assert
		Assert.assertEquals(expectedStatisticListSize, actualStatisticsListSize);
	}

	@Test
	public void testAdministrativeStatisticsGetSoldTicketsByMovie()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());
		Movie createdMovie = this.createTestMovie(null, null);
		Movie otherMovie = this.createTestMovie(null, null);
		Cinema createdCinema1 = this.createTestCinema();
		Cinema createdCinema2 = this.createTestCinema();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 15);
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.add(Calendar.DAY_OF_MONTH, 4);
		Date endDate = new Date(calendar.getTimeInMillis());

		calendar.add(Calendar.DAY_OF_MONTH, -2);
		this.createTestTicket(createdCinema1.getId(), 1, createdMovie.getId(),
				0, 0, new Date(calendar.getTimeInMillis()), 0, 0);

		this.createTestTicket(createdCinema1.getId(), 2, otherMovie.getId(), 0,
				0, new Date(calendar.getTimeInMillis()), 0, 0);

		this.createTestTicket(createdCinema2.getId(), 3, createdMovie.getId(),
				0, 0, new Date(calendar.getTimeInMillis()), 0, 0);

		calendar.add(Calendar.DAY_OF_MONTH, -1);
		this.createTestTicket(createdCinema1.getId(), 4, createdMovie.getId(),
				0, 0, new Date(calendar.getTimeInMillis()), 0, 0);

		calendar.add(Calendar.DAY_OF_MONTH, 2);
		this.createTestTicket(createdCinema1.getId(), 5, createdMovie.getId(),
				0, 0, new Date(calendar.getTimeInMillis()), 0, 0);

		calendar.add(Calendar.DAY_OF_MONTH, 2);
		this.createTestTicket(createdCinema1.getId(), 6, createdMovie.getId(),
				0, 0, new Date(calendar.getTimeInMillis()), 0, 0);

		calendar.add(Calendar.DAY_OF_MONTH, -6);
		this.createTestTicket(createdCinema1.getId(), 7, createdMovie.getId(),
				0, 0, new Date(calendar.getTimeInMillis()), 0, 0);

		int expectedWholeStatisticsListSize = 4;
		int actualWholeStatisticsListSize = 0;

		int expectedCinema1StatisticsListSize = 3;
		int actualCinema1StatisticsListSize = 0;

		// act
		actualWholeStatisticsListSize = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByMovie(startDate, endDate, createdMovie, null);

		actualCinema1StatisticsListSize = cinemaSystem
				.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByMovie(startDate, endDate, createdMovie,
						createdCinema1);

		// assert
		Assert.assertEquals(expectedWholeStatisticsListSize,
				actualWholeStatisticsListSize);
		Assert.assertEquals(expectedCinema1StatisticsListSize,
				actualCinema1StatisticsListSize);
	}

	@Test
	public void testCinemaStatisticsGetSoldTicketsByDay()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema otherCinema = this.createTestCinema();

		int days;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		days = (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 2) % 7;
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		days = (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 2) % 7;
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		days = (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 2) % 7;
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date properDate = new Date(calendar.getTimeInMillis());
		days = (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 3) % 7;
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date otherDayDate = new Date(calendar.getTimeInMillis());

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				properDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				properDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				otherDayDate, 0, 0);

		this.createTestTicket(otherCinema.getId(), 1, 0, 0, 0, properDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				earlierDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0, laterDate,
				0, 0);

		int expectedTicketsCount = 2;
		int actualTicketsCount = 0;

		// act
		actualTicketsCount = cinemaSystem.getCinemaManagerModule()
				.getManagerStatistics()
				.getSoldTicketsByDay(startDate, endDate, Calendar.MONDAY);

		// assert
		Assert.assertEquals(expectedTicketsCount, actualTicketsCount);
	}

	@Test
	public void testAdministrativeStatisticsGetSoldTicketsByDay()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema createdCinema1 = this.createTestCinema();
		Cinema createdCinema2 = this.createTestCinema();

		int days;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		days = (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 2) % 7;
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		days = (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 2) % 7;
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		days = (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 2) % 7;
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date properDate = new Date(calendar.getTimeInMillis());
		days = (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 3) % 7;
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date otherDayDate = new Date(calendar.getTimeInMillis());

		int ticketsCountBefore = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByDay(startDate, endDate, Calendar.MONDAY);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, properDate,
				0, 0);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, properDate,
				0, 0);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, otherDayDate,
				0, 0);

		this.createTestTicket(createdCinema2.getId(), 1, 0, 0, 0, properDate,
				0, 0);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, earlierDate,
				0, 0);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, laterDate, 0,
				0);

		int expectedTicketsCinema1Count = 2;
		int actualTicketsCinema1Count = 0;

		int expectedTicketsAllCinemasCount = 3 + ticketsCountBefore;
		int actualTicketsAllCinemasCount = 0;

		// act
		actualTicketsCinema1Count = cinemaSystem
				.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByDay(startDate, endDate, Calendar.MONDAY,
						createdCinema1);

		actualTicketsAllCinemasCount = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByDay(startDate, endDate, Calendar.MONDAY);

		// assert
		Assert.assertEquals(expectedTicketsCinema1Count,
				actualTicketsCinema1Count);
		Assert.assertEquals(expectedTicketsAllCinemasCount,
				actualTicketsAllCinemasCount);
	}

	@Test
	public void testCinemaStatisticsGetSoldTicketsByGenre()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema otherCinema = this.createTestCinema();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		Date properDate = new Date(calendar.getTimeInMillis());

		Genre createdGenre = Genre.Drama;
		Genre otherGenre = Genre.Action;

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				properDate, 0, 0, 0, createdGenre);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				properDate, 0, 0, 0, createdGenre);

		this.createTestTicket(otherCinema.getId(), 1, 0, 0, 0, properDate, 0,
				0, 0, otherGenre);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				earlierDate, 0, 0, 0, createdGenre);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0, laterDate,
				0, 0, 0, createdGenre);

		int expectedTicketsCount = 2;
		int actualTicketsCount = 0;

		// act
		actualTicketsCount = cinemaSystem.getCinemaManagerModule()
				.getManagerStatistics()
				.getSoldTicketsByGenre(startDate, endDate, createdGenre);

		// assert
		Assert.assertEquals(expectedTicketsCount, actualTicketsCount);
	}

	@Test
	public void testAdministrativeStatisticsGetSoldTicketsByGenre()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema createdCinema1 = this.createTestCinema();
		Cinema createdCinema2 = this.createTestCinema();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		Date properDate = new Date(calendar.getTimeInMillis());

		Genre createdGenre = Genre.Drama;
		Genre otherGenre = Genre.Action;

		int ticketsCountBefore = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByGenre(startDate, endDate, createdGenre);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, properDate,
				0, 0, 0, createdGenre);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, properDate,
				0, 0, 0, createdGenre);

		this.createTestTicket(createdCinema2.getId(), 1, 0, 0, 0, properDate,
				0, 0, 0, createdGenre);

		this.createTestTicket(createdCinema2.getId(), 1, 0, 0, 0, properDate,
				0, 0, 0, otherGenre);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, earlierDate,
				0, 0, 0, createdGenre);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, laterDate, 0,
				0, 0, createdGenre);

		int expectedTickets1Count = 2;
		int actualTickets1Count = 0;

		int expectedAllTicketsCount = 3 + ticketsCountBefore;
		int actualAllTicketsCount = 0;

		// act
		actualTickets1Count = cinemaSystem
				.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByGenre(startDate, endDate, createdGenre,
						createdCinema1);

		actualAllTicketsCount = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByGenre(startDate, endDate, createdGenre);

		// assert
		Assert.assertEquals(expectedTickets1Count, actualTickets1Count);
		Assert.assertEquals(expectedAllTicketsCount, actualAllTicketsCount);
	}

	@Test
	public void testCinemaStatisticsGetSoldTicketsByCashier()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema otherCinema = this.createTestCinema();
		User createdCashier = this.createTestUser(Role.Cashier,
				createdUser.getCinemaId());
		User otherCashier = this.createTestUser(Role.Cashier,
				createdUser.getCinemaId());

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		Date properDate = new Date(calendar.getTimeInMillis());

		this.createTestTicket(createdUser.getCinemaId(), 1, 0,
				createdCashier.getId(), 0, properDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0,
				createdCashier.getId(), 0, properDate, 0, 0);

		this.createTestTicket(otherCashier.getCinemaId(), 1, 0,
				otherCashier.getId(), 0, properDate, 0, 0);

		this.createTestTicket(otherCinema.getId(), 1, 0, 0, 0, properDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0,
				createdCashier.getId(), 0, earlierDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0,
				createdCashier.getId(), 0, laterDate, 0, 0);

		int expectedTicketsCount = 2;
		int actualTicketsCount = 0;

		// act
		actualTicketsCount = cinemaSystem.getCinemaManagerModule()
				.getManagerStatistics()
				.getSoldTicketsByCashier(startDate, endDate, createdCashier);

		// assert
		Assert.assertEquals(expectedTicketsCount, actualTicketsCount);
	}

	@Test
	public void testAdministrativeStatisticsGetSoldTicketsByCashier()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema createdCinema = this.createTestCinema();
		User createdCashier = this.createTestUser(Role.Cashier,
				createdCinema.getId());
		User otherCashier = this.createTestUser(Role.Cashier,
				createdCinema.getId());

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		Date properDate = new Date(calendar.getTimeInMillis());

		this.createTestTicket(createdCashier.getCinemaId(), 1, 0,
				createdCashier.getId(), 0, properDate, 0, 0);

		this.createTestTicket(createdCashier.getCinemaId(), 1, 0,
				createdCashier.getId(), 0, properDate, 0, 0);

		this.createTestTicket(otherCashier.getCinemaId(), 1, 0,
				otherCashier.getId(), 0, properDate, 0, 0);

		this.createTestTicket(createdCashier.getCinemaId(), 1, 0, 0, 0,
				properDate, 0, 0);

		this.createTestTicket(createdCashier.getCinemaId(), 1, 0,
				createdCashier.getId(), 0, earlierDate, 0, 0);

		this.createTestTicket(createdCashier.getCinemaId(), 1, 0,
				createdCashier.getId(), 0, laterDate, 0, 0);

		int expectedTicketsCount = 2;
		int actualTicketsCount = 0;

		// act
		actualTicketsCount = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByCashier(startDate, endDate, createdCashier);

		// assert
		Assert.assertEquals(expectedTicketsCount, actualTicketsCount);

	}

	@Test
	public void testCinemaStatisticsGetSoldTicketsByCinema()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema otherCinema = this.createTestCinema();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		Date properDate = new Date(calendar.getTimeInMillis());

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				properDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				properDate, 0, 0);

		this.createTestTicket(otherCinema.getId(), 1, 0, 0, 0, properDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				earlierDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0, laterDate,
				0, 0);

		int expectedTicketsCount = 2;
		int actualTicketsCount = 0;

		// act
		actualTicketsCount = cinemaSystem.getCinemaManagerModule()
				.getManagerStatistics()
				.getSoldTicketsByCinema(startDate, endDate);

		// assert
		Assert.assertEquals(expectedTicketsCount, actualTicketsCount);
	}

	@Test
	public void testAdministrativeStatisticsGetMoneyEarnedByCinema()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema createdCinema1 = this.createTestCinema();
		Cinema createdCinema2 = this.createTestCinema();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		Date properDate = new Date(calendar.getTimeInMillis());

		double ticket1Price = 12.88;
		double ticket2Price = 23.09;
		double ticketInOtherCinemaPrice = 45.12;

		double moneyEarnedBefore = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getMoneyEarnedByCinema(startDate, endDate);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, properDate,
				0, 0, ticket1Price);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, properDate,
				0, 0, ticket2Price);

		this.createTestTicket(createdCinema2.getId(), 1, 0, 0, 0, properDate,
				0, 0, ticketInOtherCinemaPrice);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, earlierDate,
				0, 0);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, laterDate, 0,
				0);

		double expectedCinema1MoneyEarned = ticket1Price + ticket2Price;
		double actualCinema1MoneyEarned = 0;

		double expectedAllCinemasMoneyEarned = ticket1Price + ticket2Price
				+ ticketInOtherCinemaPrice + moneyEarnedBefore;
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		expectedAllCinemasMoneyEarned = Double.valueOf(decimalFormat.format(
				expectedAllCinemasMoneyEarned).replaceAll(",", "."));
		double actualAllCinemasMoneyEarned = 0;

		// act
		actualCinema1MoneyEarned = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getMoneyEarnedByCinema(startDate, endDate, createdCinema1);

		actualAllCinemasMoneyEarned = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getMoneyEarnedByCinema(startDate, endDate);

		// assert
		Assert.assertEquals(expectedCinema1MoneyEarned,
				actualCinema1MoneyEarned, 0);
		Assert.assertEquals(expectedAllCinemasMoneyEarned,
				actualAllCinemasMoneyEarned, 0);
	}

	@Test
	public void testCinemaStatisticsGetMoneyEarnedByCinema()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.CinemaManager);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema otherCinema = this.createTestCinema();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		Date properDate = new Date(calendar.getTimeInMillis());

		double ticket1Price = 12.84;
		double ticket2Price = 27.33;

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				properDate, 0, 0, ticket1Price);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				properDate, 0, 0, ticket2Price);

		this.createTestTicket(otherCinema.getId(), 1, 0, 0, 0, properDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0,
				earlierDate, 0, 0);

		this.createTestTicket(createdUser.getCinemaId(), 1, 0, 0, 0, laterDate,
				0, 0);

		double expectedEarnedMoney = ticket1Price + ticket2Price;
		double actualEarnedMoney = 0;

		// act
		actualEarnedMoney = cinemaSystem.getCinemaManagerModule()
				.getManagerStatistics()
				.getMoneyEarnedByCinema(startDate, endDate);

		// assert
		Assert.assertEquals(expectedEarnedMoney, actualEarnedMoney, 0);
	}

	@Test
	public void testAdministrativeStatisticsGetSoldTicketsByCinema()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Administrator);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Cinema createdCinema1 = this.createTestCinema();
		Cinema createdCinema2 = this.createTestCinema();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 2);
		Date startDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 5);
		Date endDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 1);
		Date earlierDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 6);
		Date laterDate = new Date(calendar.getTimeInMillis());
		calendar.set(Calendar.MONTH, 3);
		Date properDate = new Date(calendar.getTimeInMillis());

		int ticketsCountBefore = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByCinema(startDate, endDate);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, properDate,
				0, 0);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, properDate,
				0, 0);

		this.createTestTicket(createdCinema2.getId(), 1, 0, 0, 0, properDate,
				0, 0);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, earlierDate,
				0, 0);

		this.createTestTicket(createdCinema1.getId(), 1, 0, 0, 0, laterDate, 0,
				0);

		int expectedCinema1TicketsCount = 2;
		int actualCinema1TicketsCount = 0;

		int expectedAllCinemasTicketsCount = 3 + ticketsCountBefore;
		int actualAllCinemasTicketsCount = 0;

		// act
		actualCinema1TicketsCount = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByCinema(startDate, endDate, createdCinema1);

		actualAllCinemasTicketsCount = cinemaSystem.getAdministrativeModule()
				.getAdministrativeStatistics()
				.getSoldTicketsByCinema(startDate, endDate);

		// assert
		Assert.assertEquals(expectedCinema1TicketsCount,
				actualCinema1TicketsCount);
		Assert.assertEquals(expectedAllCinemasTicketsCount,
				actualAllCinemasTicketsCount);
	}

	@Test
	public void testDbDAOSeatCreateAndRead()
			throws FailedDatabaseOperationException {

		// arrange
		Auditorium createdAuditorium = this.createTestAuditorium(0);

		Seat expectedSeat = new Seat(createdAuditorium.getId(), 10);
		Seat actualSeat = null;

		// act
		DAO<Seat> seatDAO = new DbDAO<>(Seat.class);

		expectedSeat.setId(seatDAO.create(expectedSeat));

		actualSeat = seatDAO.read(expectedSeat.getId());

		// assert
		Assert.assertEquals(expectedSeat, actualSeat);

	}

	@Test
	public void testDbDAOTicketCreateAndRead()
			throws FailedDatabaseOperationException {

		// arrange
		User createdCashier = this.createTestUser(Role.Cashier);
		Show createdShow = this.createTestShow(0, null, null, null, 0, 0, 0);
		Seat createdSeat = this.createTestSeat(1, 0);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);

		Ticket expectedTicket = new Ticket(this.showPrice, TicketStatus.Sold,
				new Date(calendar.getTimeInMillis()), createdCashier.getId(),
				createdShow.getId(), createdSeat.getId());
		Ticket actualTicket = null;

		// act
		DAO<Ticket> ticketDAO = new DbDAO<>(Ticket.class);

		expectedTicket.setId(ticketDAO.create(expectedTicket));

		actualTicket = ticketDAO.read(expectedTicket.getId());

		// assert
		Assert.assertEquals(expectedTicket, actualTicket);

	}

	@Test
	public void testDbTicketVendorCreateTicket()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Show createdShow = this.createTestShow(createdUser.getCinemaId(), null,
				null, null, 0, 20.99, 0);
		Seat createdSeat = this
				.createTestSeat(1, createdShow.getAuditoriumId());
		Discount createdDiscount = this.createTestDiscount(24.50);

		Ticket createdTicket = null;
		double expectedPrice = 15.85;
		double actualPrice = 0;

		// act
		createdTicket = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat, createdDiscount);
		actualPrice = createdTicket.getPrice();

		// assert
		Assert.assertEquals(expectedPrice, actualPrice, 0);
		Assert.assertNull(createdTicket.getBookingId());
		Assert.assertEquals(createdTicket.getShowId(), createdShow.getId());
		Assert.assertEquals(createdTicket.getSeatId(), createdSeat.getId());
		Assert.assertEquals(createdTicket.getCashierId(), createdUser.getId());
		Assert.assertNull(createdTicket.getSellingDate());
		Assert.assertNull(createdTicket.getId());
		Assert.assertNull(createdTicket.getTicketStatus());
	}

	@Test
	public void testDbTicketVendorGetDiscounts()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Discount createdDiscount1 = this.createTestDiscount(0);
		Discount createdDiscount2 = this.createTestDiscount(0);
		Discount createdDiscount3 = this.createTestDiscount(0);

		DAO<Discount> discountDAO = new DbDAO<>(Discount.class);
		int expectedListSize = discountDAO.readAll().size();

		// act
		List<Discount> discounts = cinemaSystem.getTicketVendor()
				.getDiscounts();

		// assert
		Assert.assertEquals(expectedListSize, discounts.size());
		Assert.assertTrue(discounts.contains(createdDiscount1));
		Assert.assertTrue(discounts.contains(createdDiscount2));
		Assert.assertTrue(discounts.contains(createdDiscount3));
	}

	@Test
	public void testDbTicketVendorSellTickets()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException,
			InvalidArgumentsException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Auditorium createdAuditorium = this.createTestAuditorium(createdUser
				.getCinemaId());
		Show createdShow = this.createTestShow(createdUser.getCinemaId(), null,
				null, null, 0, 0, createdAuditorium.getId());
		Seat createdSeat1 = this.createTestSeat(1, createdAuditorium.getId());
		Seat createdSeat2 = this.createTestSeat(2, createdAuditorium.getId());

		Ticket createdTicket1 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat1, null);

		Ticket createdTicket2 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat2, null);

		Ticket createdTicketWithTakenSeat = cinemaSystem.getTicketVendor()
				.createTicket(createdShow, createdSeat2, null);

		List<Ticket> ticketsWithTakenSeat = new ArrayList<>();
		ticketsWithTakenSeat.add(createdTicket1);
		ticketsWithTakenSeat.add(createdTicket2);
		ticketsWithTakenSeat.add(createdTicketWithTakenSeat);

		List<Ticket> ticketsToSell = new ArrayList<>();
		ticketsToSell.add(createdTicket1);
		ticketsToSell.add(createdTicket2);

		boolean isExceptionThrownWhileCreatingTicketsWithSameSeats = false;
		int expectedTicketsCount = 2;
		int actualTicketsCount = 0;
		boolean isExceptionThrownWhileCreatingTicketForTakenSeat = false;
		List<Ticket> actualTickets = new ArrayList<>();

		// act
		try {
			cinemaSystem.getTicketVendor().sellTickets(ticketsWithTakenSeat);
		} catch (InvalidArgumentsException e) {
			isExceptionThrownWhileCreatingTicketsWithSameSeats = true;
		}

		List<Ticket> actualSoldTickets = cinemaSystem.getTicketVendor()
				.sellTickets(ticketsToSell);

		actualTicketsCount = actualSoldTickets.size();

		try {
			cinemaSystem.getTicketVendor().sellTickets(ticketsToSell);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage().equals("Server error")) {
				isExceptionThrownWhileCreatingTicketForTakenSeat = true;
			}
		}

		DAO<Ticket> ticketDAO = new DbDAO<>(Ticket.class);
		List<Ticket> allTickets = ticketDAO.readAll();
		for (Ticket ticket : allTickets) {
			if (ticket.getShowId().equals(createdShow.getId())) {
				actualTickets.add(ticket);
			}
		}

		// assert
		Assert.assertEquals(expectedTicketsCount, actualTicketsCount);
		Assert.assertTrue(isExceptionThrownWhileCreatingTicketsWithSameSeats);
		Assert.assertTrue(isExceptionThrownWhileCreatingTicketForTakenSeat);
		for (Ticket ticket : actualSoldTickets) {
			Assert.assertTrue(allTickets.contains(ticket));
		}
	}

	@Test
	public void testDbTicketVendorGetSeats() throws Exception {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Show createdShow = this.createTestShow(createdUser.getCinemaId(), null,
				null, null, 0, 0, 0);
		Show otherShow = this.createTestShow(createdUser.getCinemaId(), null,
				null, null, 0, 0, createdShow.getAuditoriumId());

		Seat freeSeat1 = this.createTestSeat(1, createdShow.getAuditoriumId());
		Seat freeSeat2 = this.createTestSeat(2, createdShow.getAuditoriumId());
		Seat takenSeat = this.createTestSeat(3, createdShow.getAuditoriumId());
		Seat takenSeatWithOtherShow3 = this.createTestSeat(4,
				createdShow.getAuditoriumId());

		this.createTestTicket(createdUser.getCinemaId(), 0, 0,
				createdUser.getId(), createdShow.getId(), null,
				takenSeat.getId(), 0);

		this.createTestTicket(createdUser.getCinemaId(), 0, 0,
				createdUser.getId(), otherShow.getId(), null,
				takenSeatWithOtherShow3.getId(), 0);

		int expectedFreeSeatsCount = 3;
		int expectedTakenSeatsCount = 1;

		List<Seat> actualTakenSeats = new ArrayList<>();
		List<Seat> actualFreeSeats = new ArrayList<>();

		// act
		List<SeatTakenPair> seats = cinemaSystem.getTicketVendor().getSeats(
				createdShow);

		for (SeatTakenPair seatTakenPair : seats) {
			if (seatTakenPair.getTaken() == true) {
				actualTakenSeats.add(seatTakenPair.getSeat());
			} else if (seatTakenPair.getTaken() == false) {
				actualFreeSeats.add(seatTakenPair.getSeat());
			} else {
				throw new Exception("IsTaken cannot be null");
			}
		}

		// assert
		Assert.assertEquals(expectedFreeSeatsCount, actualFreeSeats.size());
		Assert.assertEquals(expectedTakenSeatsCount, actualTakenSeats.size());
		Assert.assertTrue(actualFreeSeats.contains(freeSeat1));
		Assert.assertTrue(actualFreeSeats.contains(freeSeat2));
		Assert.assertTrue(actualFreeSeats.contains(takenSeatWithOtherShow3));
		Assert.assertFalse(actualFreeSeats.contains(takenSeat));
	}

	@Test
	public void testDbTicketVendorPrintTickets()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException,
			FileNotFoundException, UnsupportedEncodingException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		Ticket createdTicket1 = this.createTestTicket(0, 21, 0, 0, 0, null, 0,
				0);
		Ticket createdTicket2 = this.createTestTicket(0, 15, 0, 0, 0, null, 0,
				0);
		List<Ticket> tickets = new ArrayList<>();
		tickets.add(createdTicket1);
		tickets.add(createdTicket2);

		// act
		cinemaSystem.getTicketVendor().printTickets(tickets);
		File file = new File("tickets.txt");

		// assert
		Assert.assertTrue(file.exists() && !file.isDirectory());
	}

	@Test
	public void testDbTicketVendorBookTickets()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException,
			InvalidArgumentsException {

		// arrange
		User createdCashier = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdCashier.getLogin(), createdCashier.getPassword());

		Auditorium createdAuditorium = this.createTestAuditorium(createdCashier
				.getCinemaId());
		Show createdShow = this.createTestShow(createdCashier.getCinemaId(),
				null, null, null, 0, 0, createdAuditorium.getId());
		Seat createdSeat1 = this.createTestSeat(1, createdAuditorium.getId());
		Seat createdSeat2 = this.createTestSeat(2, createdAuditorium.getId());

		Ticket createdTicket1 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat1, null);

		Ticket createdTicket2 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat2, null);

		Ticket createdTicketWithTakenSeat = cinemaSystem.getTicketVendor()
				.createTicket(createdShow, createdSeat2, null);

		List<Ticket> ticketsWithTakenSeat = new ArrayList<>();
		ticketsWithTakenSeat.add(createdTicket1);
		ticketsWithTakenSeat.add(createdTicket2);
		ticketsWithTakenSeat.add(createdTicketWithTakenSeat);

		List<Ticket> ticketsToBook = new ArrayList<>();
		ticketsToBook.add(createdTicket1);
		ticketsToBook.add(createdTicket2);

		boolean isExceptionThrownWhileBookingTicketsWithSameSeats = false;
		boolean isExceptionThrownWhileBookingTicketForTakenSeat = false;
		int expectedBookedTicketsCount = 2;
		int actualBookedTicketsCount = 0;

		Booking expectedBooking = new Booking(false, this.bookingCustomerName,
				this.bookingCustomerLastName, this.bookingCustomerEmail);
		Booking actualBooking = null;

		// act
		try {
			cinemaSystem.getTicketVendor().bookTickets(ticketsWithTakenSeat,
					this.bookingCustomerName, this.bookingCustomerLastName,
					this.bookingCustomerEmail);
		} catch (InvalidArgumentsException e) {
			isExceptionThrownWhileBookingTicketsWithSameSeats = true;
		}

		expectedBooking.setId(cinemaSystem.getTicketVendor().bookTickets(
				ticketsToBook, this.bookingCustomerName,
				this.bookingCustomerLastName, this.bookingCustomerEmail));

		DAO<Booking> bookingDAO = new DbDAO<>(Booking.class);
		actualBooking = bookingDAO.read(expectedBooking.getId());

		try {
			cinemaSystem.getTicketVendor().bookTickets(ticketsToBook,
					this.bookingCustomerName, this.bookingCustomerLastName,
					this.bookingCustomerEmail);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage().equals("Server error")) {
				isExceptionThrownWhileBookingTicketForTakenSeat = true;
			}
		}

		DAO<Ticket> ticketDAO = new DbDAO<>(Ticket.class);
		List<Ticket> allTickets = ticketDAO.readAll();
		for (Ticket ticket : allTickets) {
			if (ticket.getShowId().equals(createdShow.getId())
					&& ticket.getBookingId().equals(expectedBooking.getId())
					&& ticket.getTicketStatus() == TicketStatus.Booked) {
				actualBookedTicketsCount++;
			}
		}

		// assert
		Assert.assertTrue(isExceptionThrownWhileBookingTicketsWithSameSeats);
		Assert.assertTrue(isExceptionThrownWhileBookingTicketForTakenSeat);
		Assert.assertEquals(expectedBooking, actualBooking);
		Assert.assertEquals(expectedBookedTicketsCount,
				actualBookedTicketsCount);
	}

	@Test
	public void testDbTicketVendorRealizeBooking()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException,
			InvalidArgumentsException {

		// arrange
		User createdCashier = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdCashier.getLogin(), createdCashier.getPassword());

		Auditorium createdAuditorium = this.createTestAuditorium(createdCashier
				.getCinemaId());
		Show createdShow = this.createTestShow(createdCashier.getCinemaId(),
				null, null, null, 0, 0, createdAuditorium.getId());
		Seat createdSeat1 = this.createTestSeat(1, createdAuditorium.getId());
		Seat createdSeat2 = this.createTestSeat(2, createdAuditorium.getId());

		Ticket createdTicket1 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat1, null);

		Ticket createdTicket2 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat2, null);

		List<Ticket> ticketsToBook = new ArrayList<>();
		ticketsToBook.add(createdTicket1);
		ticketsToBook.add(createdTicket2);

		int createdBookingId = cinemaSystem.getTicketVendor().bookTickets(
				ticketsToBook, this.bookingCustomerName,
				this.bookingCustomerLastName, this.bookingCustomerEmail);

		String invalidCustomerLastName = this.bookingCustomerLastName + "123";
		int invalidBookingId = createdBookingId + 2;

		boolean isExceptionThrownWhileProvidingInvalidCustomerLastName = false;
		boolean isExceptionThrownWhileProvidingInvalidBookingId = false;
		int expectedRealizedTicketsCount = 2;
		int actualRealizedTicketsCount = 0;
		boolean areAllTicketsStatusSold = false;

		// act
		try {
			cinemaSystem.getTicketVendor().realizeBooking(
					invalidCustomerLastName, createdBookingId);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrownWhileProvidingInvalidCustomerLastName = true;
			}
		}

		try {
			cinemaSystem.getTicketVendor().realizeBooking(
					this.bookingCustomerLastName, invalidBookingId);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrownWhileProvidingInvalidBookingId = true;
			}
		}

		List<Ticket> realizedTickets = cinemaSystem.getTicketVendor()
				.realizeBooking(this.bookingCustomerLastName, createdBookingId);

		actualRealizedTicketsCount = realizedTickets.size();

		areAllTicketsStatusSold = true;
		DAO<Ticket> ticketDAO = new DbDAO<>(Ticket.class);
		for (Ticket ticket : realizedTickets) {
			Ticket ticketInDatabase = ticketDAO.read(ticket.getId());
			if (ticketInDatabase.getTicketStatus() != TicketStatus.Sold) {
				areAllTicketsStatusSold = false;
			}
		}

		// assert
		Assert.assertTrue(isExceptionThrownWhileProvidingInvalidCustomerLastName);
		Assert.assertTrue(isExceptionThrownWhileProvidingInvalidBookingId);
		Assert.assertEquals(expectedRealizedTicketsCount,
				actualRealizedTicketsCount);
		Assert.assertTrue(areAllTicketsStatusSold);
	}

	@Test
	public void testDbTicketVendorCancelBooking()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException,
			InvalidArgumentsException {

		// arrange
		User createdCashier = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdCashier.getLogin(), createdCashier.getPassword());

		Auditorium createdAuditorium = this.createTestAuditorium(createdCashier
				.getCinemaId());
		Show createdShow = this.createTestShow(createdCashier.getCinemaId(),
				null, null, null, 0, 0, createdAuditorium.getId());
		Seat createdSeat1 = this.createTestSeat(1, createdAuditorium.getId());
		Seat createdSeat2 = this.createTestSeat(2, createdAuditorium.getId());

		Ticket createdTicket1 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat1, null);

		Ticket createdTicket2 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat2, null);

		List<Ticket> ticketsToBook = new ArrayList<>();
		ticketsToBook.add(createdTicket1);
		ticketsToBook.add(createdTicket2);

		int createdBookingId = cinemaSystem.getTicketVendor().bookTickets(
				ticketsToBook, this.bookingCustomerName,
				this.bookingCustomerLastName, this.bookingCustomerEmail);

		String invalidCustomerLastName = this.bookingCustomerLastName + "123";
		int invalidBookingId = createdBookingId + 10;

		boolean isExceptionThrownWhileProvidingInvalidCustomerLastName = false;
		boolean isExceptionThrownWhileProvidingInvalidBookingId = false;
		boolean areAllTicketsStatusCancelled = false;
		int expectedCancelledTicketsCount = 2;
		int actualCancelledTicketsCount = 0;
		boolean isBookingCancelled = false;

		// act
		try {
			cinemaSystem.getTicketVendor().cancelBooking(
					invalidCustomerLastName, createdBookingId);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Cancelling unsuccesful") {
				isExceptionThrownWhileProvidingInvalidCustomerLastName = true;
			}
		}

		try {
			cinemaSystem.getTicketVendor().cancelBooking(
					this.bookingCustomerLastName, invalidBookingId);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrownWhileProvidingInvalidBookingId = true;
			}
		}

		cinemaSystem.getTicketVendor().cancelBooking(
				this.bookingCustomerLastName, createdBookingId);

		DAO<Booking> bookingDAO = new DbDAO<>(Booking.class);
		isBookingCancelled = bookingDAO.read(createdBookingId).getIsCancelled();

		areAllTicketsStatusCancelled = true;
		DAO<Ticket> ticketDAO = new DbDAO<>(Ticket.class);
		for (Ticket ticket : ticketDAO.readAll()) {
			if (ticket.getBookingId() != null
					&& ticket.getBookingId().equals(createdBookingId)) {
				actualCancelledTicketsCount++;
				Ticket ticketInDatabase = ticketDAO.read(ticket.getId());
				if (ticketInDatabase.getTicketStatus() != TicketStatus.Cancelled) {
					areAllTicketsStatusCancelled = false;
				}
			}
		}

		// assert
		Assert.assertTrue(isExceptionThrownWhileProvidingInvalidCustomerLastName);
		Assert.assertTrue(isExceptionThrownWhileProvidingInvalidBookingId);
		Assert.assertEquals(expectedCancelledTicketsCount,
				actualCancelledTicketsCount);
		Assert.assertTrue(areAllTicketsStatusCancelled);
		Assert.assertTrue(isBookingCancelled);
	}

	@Test
	public void testDbTicketVendorRealizeCancelledBooking()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException,
			InvalidArgumentsException {

		// arrange
		User createdCashier = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdCashier.getLogin(), createdCashier.getPassword());

		Auditorium createdAuditorium = this.createTestAuditorium(createdCashier
				.getCinemaId());
		Show createdShow = this.createTestShow(createdCashier.getCinemaId(),
				null, null, null, 0, 0, createdAuditorium.getId());
		Seat createdSeat1 = this.createTestSeat(1, createdAuditorium.getId());
		Seat createdSeat2 = this.createTestSeat(2, createdAuditorium.getId());

		Ticket createdTicket1 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat1, null);

		Ticket createdTicket2 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat2, null);

		List<Ticket> ticketsToBook = new ArrayList<>();
		ticketsToBook.add(createdTicket1);
		ticketsToBook.add(createdTicket2);

		int createdBookingId = cinemaSystem.getTicketVendor().bookTickets(
				ticketsToBook, this.bookingCustomerName,
				this.bookingCustomerLastName, this.bookingCustomerEmail);

		cinemaSystem.getTicketVendor().cancelBooking(
				this.bookingCustomerLastName, createdBookingId);

		boolean isExceptionThrownWhileRealizingCancelledBooking = false;

		// act
		try {
			cinemaSystem.getTicketVendor().realizeBooking(
					this.bookingCustomerLastName, createdBookingId);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrownWhileRealizingCancelledBooking = true;
			}
		}

		// assert
		Assert.assertTrue(isExceptionThrownWhileRealizingCancelledBooking);
	}

	@Test
	public void testDbTicketVendorCancelCancelledBooking()
			throws FailedDatabaseOperationException,
			InvalidCredentialsException, InvalidUserRoleException,
			InvalidArgumentsException {

		// arrange
		User createdCashier = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdCashier.getLogin(), createdCashier.getPassword());

		Auditorium createdAuditorium = this.createTestAuditorium(createdCashier
				.getCinemaId());
		Show createdShow = this.createTestShow(createdCashier.getCinemaId(),
				null, null, null, 0, 0, createdAuditorium.getId());
		Seat createdSeat1 = this.createTestSeat(1, createdAuditorium.getId());
		Seat createdSeat2 = this.createTestSeat(2, createdAuditorium.getId());

		Ticket createdTicket1 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat1, null);

		Ticket createdTicket2 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat2, null);

		List<Ticket> ticketsToBook = new ArrayList<>();
		ticketsToBook.add(createdTicket1);
		ticketsToBook.add(createdTicket2);

		int createdBookingId = cinemaSystem.getTicketVendor().bookTickets(
				ticketsToBook, this.bookingCustomerName,
				this.bookingCustomerLastName, this.bookingCustomerEmail);

		cinemaSystem.getTicketVendor().cancelBooking(
				this.bookingCustomerLastName, createdBookingId);

		boolean isExceptionThrownWhileCancellingCancelledBooking = false;

		// act
		try {
			cinemaSystem.getTicketVendor().cancelBooking(
					this.bookingCustomerLastName, createdBookingId);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrownWhileCancellingCancelledBooking = true;
			}
		}

		// assert
		Assert.assertTrue(isExceptionThrownWhileCancellingCancelledBooking);
	}

	@Test
	public void testDbTicketVendorCancelRealizedBooking()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException,
			InvalidArgumentsException {

		// arrange
		User createdCashier = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdCashier.getLogin(), createdCashier.getPassword());

		Auditorium createdAuditorium = this.createTestAuditorium(createdCashier
				.getCinemaId());
		Show createdShow = this.createTestShow(createdCashier.getCinemaId(),
				null, null, null, 0, 0, createdAuditorium.getId());
		Seat createdSeat1 = this.createTestSeat(1, createdAuditorium.getId());
		Seat createdSeat2 = this.createTestSeat(2, createdAuditorium.getId());

		Ticket createdTicket1 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat1, null);

		Ticket createdTicket2 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat2, null);

		List<Ticket> ticketsToBook = new ArrayList<>();
		ticketsToBook.add(createdTicket1);
		ticketsToBook.add(createdTicket2);

		int createdBookingId = cinemaSystem.getTicketVendor().bookTickets(
				ticketsToBook, this.bookingCustomerName,
				this.bookingCustomerLastName, this.bookingCustomerEmail);

		cinemaSystem.getTicketVendor().realizeBooking(
				this.bookingCustomerLastName, createdBookingId);

		boolean isExceptionThrownWhileCancellingRealizedBooking = false;

		// act
		try {
			cinemaSystem.getTicketVendor().cancelBooking(
					this.bookingCustomerLastName, createdBookingId);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrownWhileCancellingRealizedBooking = true;
			}
		}

		// assert
		Assert.assertTrue(isExceptionThrownWhileCancellingRealizedBooking);
	}

	@Test
	public void testDbTicketVendorRealizeRealizedBooking()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException,
			InvalidArgumentsException {

		// arrange
		User createdCashier = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdCashier.getLogin(), createdCashier.getPassword());

		Auditorium createdAuditorium = this.createTestAuditorium(createdCashier
				.getCinemaId());
		Show createdShow = this.createTestShow(createdCashier.getCinemaId(),
				null, null, null, 0, 0, createdAuditorium.getId());
		Seat createdSeat1 = this.createTestSeat(1, createdAuditorium.getId());
		Seat createdSeat2 = this.createTestSeat(2, createdAuditorium.getId());

		Ticket createdTicket1 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat1, null);

		Ticket createdTicket2 = cinemaSystem.getTicketVendor().createTicket(
				createdShow, createdSeat2, null);

		List<Ticket> ticketsToBook = new ArrayList<>();
		ticketsToBook.add(createdTicket1);
		ticketsToBook.add(createdTicket2);

		int createdBookingId = cinemaSystem.getTicketVendor().bookTickets(
				ticketsToBook, this.bookingCustomerName,
				this.bookingCustomerLastName, this.bookingCustomerEmail);

		cinemaSystem.getTicketVendor().realizeBooking(
				this.bookingCustomerLastName, createdBookingId);

		boolean isExceptionThrownWhileRealizingRealizedBooking = false;

		// act
		try {
			cinemaSystem.getTicketVendor().realizeBooking(
					this.bookingCustomerLastName, createdBookingId);
		} catch (FailedDatabaseOperationException e) {
			if (e.getMessage() == "Server error") {
				isExceptionThrownWhileRealizingRealizedBooking = true;
			}
		}

		// assert
		Assert.assertTrue(isExceptionThrownWhileRealizingRealizedBooking);
	}

	@Test
	public void testDbTicketGetCustomersBooking()
			throws InvalidCredentialsException,
			FailedDatabaseOperationException, InvalidUserRoleException {

		// arrange
		User createdUser = this.createTestUser(Role.Cashier);
		CinemaSystem cinemaSystem = this.createCinemaSystemAndLogin(
				createdUser.getLogin(), createdUser.getPassword());

		String customerName = "testowe imie";
		String customerLastName = "testowe nazwisko";
		String customerEmail = UUID.randomUUID().toString().substring(0, 6)
				+ "@gmail.com";

		Booking createdBooking1 = this.createTestBooking(customerName,
				customerLastName, customerEmail);

		Ticket createdTicket1InBooking1 = this.createTestTicket(
				createdUser.getCinemaId(), 8, 0, createdUser.getId(), 0, null,
				0, createdBooking1.getId());

		Ticket createdTicket2InBooking1 = this.createTestTicket(
				createdUser.getCinemaId(), 3, 0, createdUser.getId(), 0, null,
				0, createdBooking1.getId());

		Booking createdBooking2 = this.createTestBooking(customerName,
				customerLastName, customerEmail);

		Ticket createdTicket1InBooking2 = this.createTestTicket(
				createdUser.getCinemaId(), 2, 0, createdUser.getId(), 0, null,
				0, createdBooking2.getId());

		Map<Booking, List<Ticket>> actualBookingTicketsMap = null;
		List<Ticket> actualTicketsAssignedToBooking1 = new ArrayList<>();
		List<Ticket> actualTicketsAssignedToBooking2 = new ArrayList<>();
		int expectedBookingsCount = 2;
		int expectedTicketsList1Size = 2;
		int expectedTicketsList2Size = 1;

		// act
		actualBookingTicketsMap = cinemaSystem.getTicketVendor()
				.getCustomerBookings(customerLastName, customerEmail);

		for (Entry<Booking, List<Ticket>> entry : actualBookingTicketsMap
				.entrySet()) {
			if (entry.getKey().equals(createdBooking1)) {
				for (Ticket ticket : entry.getValue()) {
					actualTicketsAssignedToBooking1.add(ticket);
				}
			} else if (entry.getKey().equals(createdBooking2)) {
				for (Ticket ticket : entry.getValue()) {
					actualTicketsAssignedToBooking2.add(ticket);
				}
			} else {
				Assert.fail("Invalid booking");
			}
		}

		// assert
		Assert.assertEquals(expectedBookingsCount, actualBookingTicketsMap
				.keySet().size());
		Assert.assertTrue(actualBookingTicketsMap.containsKey(createdBooking1));
		Assert.assertTrue(actualBookingTicketsMap.containsKey(createdBooking2));
		Assert.assertEquals(expectedTicketsList1Size,
				actualTicketsAssignedToBooking1.size());
		Assert.assertEquals(expectedTicketsList2Size,
				actualTicketsAssignedToBooking2.size());
		Assert.assertTrue(actualTicketsAssignedToBooking1
				.contains(createdTicket1InBooking1));
		Assert.assertTrue(actualTicketsAssignedToBooking1
				.contains(createdTicket2InBooking1));
		Assert.assertTrue(actualTicketsAssignedToBooking2
				.contains(createdTicket1InBooking2));
	}

	@Test
	public void testDbDAOBookingCreateAndRead()
			throws FailedDatabaseOperationException {

		// arrange
		Booking expectedBooking = new Booking(false, "testowe imie",
				"testowe nazwisko", "testowymejl@gmail.com");
		Booking actualBooking = null;

		// act
		DAO<Booking> bookingDAO = new DbDAO<>(Booking.class);
		expectedBooking.setId(bookingDAO.create(expectedBooking));
		actualBooking = bookingDAO.read(expectedBooking.getId());

		// assert
		Assert.assertEquals(expectedBooking, actualBooking);
	}
}
