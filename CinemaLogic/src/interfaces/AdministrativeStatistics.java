package interfaces;

import java.util.Date;

import model.Cinema;
import model.Genre;
import model.Movie;
import model.User;
import exceptions.FailedDatabaseOperationException;

/**
 * The Interface AdministrativeStatistics. Provides methods to get
 * administrative statistics.
 */
public interface AdministrativeStatistics extends ManagerStatistics {

	/**
	 * Gets sold tickets by movie in specified cinema.
	 * 
	 * @param startDate
	 *            the start date to search
	 * @param endDate
	 *            the end date to search
	 * @param movie
	 *            the movie to search
	 * @param cinema
	 *            the cinema to search
	 * @return sold tickets by movie in specified cinema
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int getSoldTicketsByMovie(Date startDate, Date endDate, Movie movie,
			Cinema cinema) throws FailedDatabaseOperationException;

	/**
	 * Gets sold tickets by day in specified cinema.
	 * 
	 * @param startDate
	 *            the start date to search
	 * @param endDate
	 *            the end date to search
	 * @param dayOfWeek
	 *            the day of week to search
	 * @param cinema
	 *            the cinema to search
	 * @return sold tickets by day in specified cinema
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int getSoldTicketsByDay(Date startDate, Date endDate, int dayOfWeek,
			Cinema cinema) throws FailedDatabaseOperationException;

	/**
	 * Gets the sold tickets by genre in specified cinema.
	 * 
	 * @param startDate
	 *            the start date to search
	 * @param endDate
	 *            the end date to search
	 * @param genre
	 *            the genre to search
	 * @param cinema
	 *            the cinema to search
	 * @return sold tickets by genre in specified cinema
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int getSoldTicketsByGenre(Date startDate, Date endDate, Genre genre,
			Cinema cinema) throws FailedDatabaseOperationException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.ManagerStatistics#getSoldTicketsByCashier(java.util.Date,
	 * java.util.Date, model.User)
	 */
	int getSoldTicketsByCashier(Date startDate, Date endDate, User cashier)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the sold tickets by specified cinema.
	 * 
	 * @param startDate
	 *            the start date to search
	 * @param endDate
	 *            the end date to search
	 * @param cinema
	 *            the cinema to search
	 * @return sold tickets by cinema
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int getSoldTicketsByCinema(Date startDate, Date endDate, Cinema cinema)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the money earned by specified cinema.
	 * 
	 * @param startDate
	 *            the start date to search
	 * @param endDate
	 *            the end date to search
	 * @param cinema
	 *            the cinema to search
	 * @return the money earned by cinema to search
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	double getMoneyEarnedByCinema(Date startDate, Date endDate, Cinema cinema)
			throws FailedDatabaseOperationException;
}
