package interfaces;

import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidCredentialsException;
import model.User;

/**
 * The Interface UserAccountManager. Provides methods to manage authenticated
 * user's account.
 */
public interface UserAccountManager {

	/**
	 * Gets the authenticated user.
	 * 
	 * @return the authenticated user
	 */
	User getAuthenticatedUser();

	/**
	 * Change user's password.
	 * 
	 * @param oldPassword
	 *            the old password
	 * @param newPassword
	 *            the new password
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 * @throws InvalidCredentialsException
	 *             the invalid credentials exception
	 */
	void changePassword(String oldPassword, String newPassword)
			throws FailedDatabaseOperationException,
			InvalidCredentialsException;
}
