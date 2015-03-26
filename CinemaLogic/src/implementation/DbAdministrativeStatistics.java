package implementation;

import interfaces.AdministrativeStatistics;

import java.util.Date;

import model.Genre;
import model.Movie;
import exceptions.FailedDatabaseOperationException;

class DbAdministrativeStatistics extends DbStatistics implements
		AdministrativeStatistics {

	public DbAdministrativeStatistics() {
		super();
	}

	@Override
	public int getSoldTicketsByDay(Date startDate, Date endDate, int dayOfWeek)
			throws FailedDatabaseOperationException {

		return this.getSoldTicketsByDay(startDate, endDate, dayOfWeek, null);
	}

	@Override
	public int getSoldTicketsByMovie(Date startDate, Date endDate, Movie movie)
			throws FailedDatabaseOperationException {

		return this.getSoldTicketsByMovie(startDate, endDate, movie, null);
	}

	@Override
	public int getSoldTicketsByGenre(Date startDate, Date endDate, Genre genre)
			throws FailedDatabaseOperationException {

		return this.getSoldTicketsByGenre(startDate, endDate, genre, null);
	}

	@Override
	public int getSoldTicketsByCinema(Date startDate, Date endDate)
			throws FailedDatabaseOperationException {

		return this.getSoldTicketsByCinema(startDate, endDate, null);
	}

	@Override
	public double getMoneyEarnedByCinema(Date startDate, Date endDate)
			throws FailedDatabaseOperationException {

		return this.getMoneyEarnedByCinema(startDate, endDate, null);
	}

}
