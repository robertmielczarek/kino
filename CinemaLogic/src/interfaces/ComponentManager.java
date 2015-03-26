package interfaces;

import java.util.List;

import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidUserRoleException;

/**
 * The Interface ComponentManager. Provides methods to manage cinema components.
 * 
 * @param <T>
 *            cinema component to manage type
 */
public interface ComponentManager<T> {

	/**
	 * Gets all managed components of type T.
	 * 
	 * @return all managed components
	 * @throws InvalidUserRoleException
	 *             the invalid user role exception
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	List<T> getAllManaged() throws InvalidUserRoleException,
			FailedDatabaseOperationException;

	/**
	 * Adds managed component T.
	 * 
	 * @param t
	 *            the component to add
	 * @return the added component id
	 * @throws InvalidUserRoleException
	 *             the invalid user role exception
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int addManaged(T t) throws InvalidUserRoleException,
			FailedDatabaseOperationException;

	/**
	 * Delete managed component of type T.
	 * 
	 * @param t
	 *            the component to delete
	 * @throws InvalidUserRoleException
	 *             the invalid user role exception
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	void deleteManaged(T t) throws InvalidUserRoleException,
			FailedDatabaseOperationException;

	/**
	 * Edits the managed component of type T.
	 * 
	 * @param newT
	 *            the new, edited component
	 * @throws InvalidUserRoleException
	 *             the invalid user role exception
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	void editManaged(T newT) throws InvalidUserRoleException,
			FailedDatabaseOperationException;
}
