package interfaces;

import java.util.Date;

import model.Genre;
import model.Movie;
import model.User;
import exceptions.FailedDatabaseOperationException;

/**
 * The Interface ManagerStatistics. Provides methods to generate manager
 * statistics.
 */
public interface ManagerStatistics {

	/**
	 * Gets sold tickets by movie in manager's cinema.
	 * 
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param movie
	 *            the movie to search
	 * @return the sold tickets by movie
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int getSoldTicketsByMovie(Date startDate, Date endDate, Movie movie)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the sold tickets by day in manager's cinema.
	 * 
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param dayOfWeek
	 *            the day of week to search
	 * @return the sold tickets by specified day
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int getSoldTicketsByDay(Date startDate, Date endDate, int dayOfWeek)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the sold tickets by genre in manager's cinema.
	 * 
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param genre
	 *            the genre to search
	 * @return the sold tickets by specified genre
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int getSoldTicketsByGenre(Date startDate, Date endDate, Genre genre)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the sold tickets by cashier.
	 * 
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param cashier
	 *            the cashier to search
	 * @return the sold tickets by specified cashier
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int getSoldTicketsByCashier(Date startDate, Date endDate, User cashier)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the sold tickets in manager's cinema.
	 * 
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the sold tickets by cinema
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int getSoldTicketsByCinema(Date startDate, Date endDate)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the money earned in manager's cinema.
	 * 
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the money earned by cinema
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	double getMoneyEarnedByCinema(Date startDate, Date endDate)
			throws FailedDatabaseOperationException;
}
