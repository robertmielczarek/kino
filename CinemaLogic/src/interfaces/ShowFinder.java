package interfaces;

import java.util.Date;
import java.util.List;

import model.Genre;
import model.Show;
import exceptions.FailedDatabaseOperationException;

/**
 * The Interface ShowFinder. Provides methods to search shows using various
 * criteria.
 */
public interface ShowFinder {

	/**
	 * Gets the shows in user's cinema by title.
	 * 
	 * @param title
	 *            the title to search
	 * @return the shows by title
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	List<Show> getShowsByTitle(String title)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the shows in specified day.
	 * 
	 * @param date
	 *            the date to search
	 * @return the shows in specified day
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	List<Show> getShowsByDate(Date date)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the shows in user's cinema by time.
	 * 
	 * @param time
	 *            the time to start search
	 * @param hoursDiff
	 *            the hours difference from time to search
	 * @return the shows by time
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	List<Show> getShowsByTime(Date time, int hoursDiff)
			throws FailedDatabaseOperationException;

	/**
	 * Gets the shows in user's cinema by genre.
	 * 
	 * @param genre
	 *            the genre to search
	 * @return the shows by genre
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	List<Show> getShowsByGenre(Genre genre)
			throws FailedDatabaseOperationException;
}
