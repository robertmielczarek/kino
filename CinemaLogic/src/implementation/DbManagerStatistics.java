package implementation;

import interfaces.ManagerStatistics;

import java.util.Date;

import model.Genre;
import model.Movie;
import model.User;
import exceptions.FailedDatabaseOperationException;

class DbManagerStatistics extends DbStatistics implements ManagerStatistics {

	private User authenticatedManager;

	public DbManagerStatistics(User user) {
		super();
		this.authenticatedManager = user;
	}

	public int getSoldTicketsByMovie(Date startDate, Date endDate, Movie movie)
			throws FailedDatabaseOperationException {

		return this.getSoldTicketsByMovie(startDate, endDate, movie,
				this.authenticatedManager.getCinema());
	}

	public int getSoldTicketsByDay(Date startDate, Date endDate, int dayOfWeek)
			throws FailedDatabaseOperationException {

		return this.getSoldTicketsByDay(startDate, endDate, dayOfWeek,
				this.authenticatedManager.getCinema());
	}

	public int getSoldTicketsByGenre(Date startDate, Date endDate, Genre genre)
			throws FailedDatabaseOperationException {

		return this.getSoldTicketsByGenre(startDate, endDate, genre,
				this.authenticatedManager.getCinema());
	}

	public int getSoldTicketsByCinema(Date startDate, Date endDate)
			throws FailedDatabaseOperationException {

		return this.getSoldTicketsByCinema(startDate, endDate,
				this.authenticatedManager.getCinema());
	}

	public double getMoneyEarnedByCinema(Date startDate, Date endDate)
			throws FailedDatabaseOperationException {

		return this.getMoneyEarnedByCinema(startDate, endDate,
				this.authenticatedManager.getCinema());
	}

}
