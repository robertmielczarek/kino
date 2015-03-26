package interfaces;

import model.User;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidCredentialsException;

/**
 * The Interface Authenticator. Provides method to authenticate user in system.
 */
public interface Authenticator {

	/**
	 * Authenticate.
	 * 
	 * @return the user used to authenticate
	 * @throws InvalidCredentialsException
	 *             the invalid credentials exception
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	User authenticate() throws InvalidCredentialsException,
			FailedDatabaseOperationException;
}
