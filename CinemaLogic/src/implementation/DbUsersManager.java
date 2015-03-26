package implementation;

import interfaces.UsersManager;

import java.util.ArrayList;
import java.util.List;

import model.Role;
import model.User;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidUserRoleException;

class DbUsersManager extends DbComponentManager<User> implements UsersManager {

	public DbUsersManager(User user) {
		super(user, User.class);
	}

	public List<User> getAllManaged() throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		List<User> allUsers = this.componentDAO.readAll();
		List<User> managedUsers = new ArrayList<User>();

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage users");

		case Administrator:
			managedUsers = allUsers;
			break;

		case CinemaManager:
			for (User user : allUsers) {
				if ((user.getRole() == Role.Cashier || user.getRole() == Role.CinemaManager)
						&& user.getCinemaId().equals(
								this.authenticatedUser.getCinemaId())) {
					managedUsers.add(user);
				}
			}
			break;

		default:
			throw new InvalidUserRoleException();
		}

		return managedUsers;
	}

	public int addManaged(User t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage users");

		case Administrator:
			return this.componentDAO.create(t);

		case CinemaManager:
			switch (t.getRole()) {

			case Administrator:
				throw new InvalidUserRoleException(
						"Cinema manager cannot manage administrators");

			case Cashier:
			case CinemaManager:
				if (t.getCinemaId().equals(authenticatedUser.getCinemaId())) {
					return this.componentDAO.create(t);
				} else {
					throw new InvalidUserRoleException(
							"Cannot manage foreign cinema users");
				}

			default:
				throw new InvalidUserRoleException();
			}

		default:
			throw new InvalidUserRoleException();
		}

	}

	public void deleteManaged(User t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {
		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage users");

		case Administrator:
			this.componentDAO.delete(t.getId());
			break;

		case CinemaManager:
			this.componentDAO.createForeign(t);
			switch (t.getRole()) {

			case Administrator:
				throw new InvalidUserRoleException(
						"Cinema manager cannot manage administrators");

			case Cashier:
			case CinemaManager:
				if (t.getCinemaId().equals(authenticatedUser.getCinemaId())) {
					this.componentDAO.delete(t.getId());
				} else {
					throw new InvalidUserRoleException(
							"Cannot manage foreign cinema users");
				}
				break;

			default:
				throw new InvalidUserRoleException();
			}
			break;

		default:
			throw new InvalidUserRoleException();
		}

	}

	public void editManaged(User newT) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage users");

		case Administrator:
			this.componentDAO.update(newT);
			break;

		case CinemaManager:
			User oldUser = this.componentDAO.read(newT.getId());

			if (oldUser.getRole() == Role.Administrator
					|| newT.getRole() == Role.Administrator) {
				throw new InvalidUserRoleException(
						"Cinema manager cannot manage administrators");
			}

			if (oldUser.getCinemaId().equals(
					this.authenticatedUser.getCinemaId())
					&& newT.getCinemaId().equals(
							this.authenticatedUser.getCinemaId())) {
				this.componentDAO.update(newT);
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema users");
			}

			break;

		default:
			throw new InvalidUserRoleException();
		}
	}

}
