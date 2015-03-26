package implementation;

import interfaces.AuditoriumsManager;

import java.util.ArrayList;
import java.util.List;

import model.Auditorium;
import model.User;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidUserRoleException;

class DbAuditoriumsManager extends DbComponentManager<Auditorium> implements
		AuditoriumsManager {

	public DbAuditoriumsManager(User user) {
		super(user, Auditorium.class);
	}

	public List<Auditorium> getAllManaged()
			throws FailedDatabaseOperationException, InvalidUserRoleException {

		List<Auditorium> allAuditoriums = this.componentDAO.readAll();
		List<Auditorium> managedAuditoriums = new ArrayList<Auditorium>();

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException(
					"Cashier cannot manage auditoriums");

		case Administrator:
			managedAuditoriums = allAuditoriums;
			break;

		case CinemaManager:
			for (Auditorium auditorium : allAuditoriums) {
				if (auditorium.getCinemaId().equals(
						this.authenticatedUser.getCinemaId())) {
					managedAuditoriums.add(auditorium);
				}
			}
			break;

		default:
			throw new InvalidUserRoleException();
		}

		return managedAuditoriums;
	}

	public int addManaged(Auditorium t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException(
					"Cashier cannot manage auditoriums");

		case Administrator:
			return this.componentDAO.create(t);

		case CinemaManager:
			if (t.getCinemaId().equals(this.authenticatedUser.getCinemaId())) {
				return this.componentDAO.create(t);
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema auditoriums");
			}

		default:
			throw new InvalidUserRoleException();
		}
	}

	public void deleteManaged(Auditorium t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException(
					"Cashier cannot manage auditoriums");

		case Administrator:
			this.componentDAO.delete(t.getId());
			break;

		case CinemaManager:
			if (t.getCinemaId().equals(this.authenticatedUser.getCinemaId())) {
				this.componentDAO.delete(t.getId());
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema auditoriums");
			}
			break;

		default:
			throw new InvalidUserRoleException();
		}
	}

	public void editManaged(Auditorium newT) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException(
					"Cashier cannot manage auditoriums");

		case Administrator:
			this.componentDAO.update(newT);
			break;

		case CinemaManager:
			if (this.componentDAO.read(newT.getId()).getCinemaId()
					.equals(this.authenticatedUser.getCinemaId())) {
				this.componentDAO.update(newT);
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema auditoriums");
			}
			break;

		default:
			throw new InvalidUserRoleException();
		}
	}

}
