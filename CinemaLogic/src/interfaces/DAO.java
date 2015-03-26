package interfaces;

import java.util.List;

import exceptions.FailedDatabaseOperationException;

/**
 * The Interface DAO. Provides base operations on cinema components.
 * 
 * @param <T>
 *            the cinema component to operate
 */
public interface DAO<T extends RestElement> {

	/**
	 * Creates the component of type T.
	 * 
	 * @param object
	 *            the object to create
	 * @return the created object id
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	int create(T object) throws FailedDatabaseOperationException;

	/**
	 * Read the cinema component of given id.
	 * 
	 * @param objectId
	 *            the component id
	 * @return the read component
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	T read(int objectId) throws FailedDatabaseOperationException;

	/**
	 * Update existing cinema component.
	 * 
	 * @param object
	 *            the edited component
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	void update(T object) throws FailedDatabaseOperationException;

	/**
	 * Delete cinema component.
	 * 
	 * @param objectId
	 *            the component id
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	void delete(int objectId) throws FailedDatabaseOperationException;

	/**
	 * Read all cinema components of type T.
	 * 
	 * @return the components list
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	List<T> readAll() throws FailedDatabaseOperationException;

	/**
	 * Creates the foreign components in cinema component of type T.
	 * 
	 * @param object
	 *            the component to create foreign
	 * @throws FailedDatabaseOperationException
	 *             the failed database operation exception
	 */
	void createForeign(T object) throws FailedDatabaseOperationException;
}
