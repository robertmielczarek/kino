package implementation;

import interfaces.DAO;
import interfaces.UserAccountManager;
import model.User;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidCredentialsException;

class DbUserAccountManager implements UserAccountManager {

	private DAO<User> usersDAO;
	private User authenticatedUser;

	public DbUserAccountManager(User authenticatedUser)
			throws FailedDatabaseOperationException {

		this.usersDAO = new DbDAO<>(User.class);
		this.authenticatedUser = authenticatedUser;
	}

	public User getAuthenticatedUser() {
		return this.authenticatedUser;
	}

	public void changePassword(String oldPassword, String newPassword)
			throws FailedDatabaseOperationException,
			InvalidCredentialsException {

		User userInDatabase = this.usersDAO
				.read(this.authenticatedUser.getId());
		if (userInDatabase.getLogin().equals(this.authenticatedUser.getLogin())
				&& userInDatabase.getPassword().equals(oldPassword)) {

			this.authenticatedUser.setPassword(newPassword);
			this.usersDAO.update(this.authenticatedUser);

		} else {
			throw new InvalidCredentialsException();
		}
	}

}
