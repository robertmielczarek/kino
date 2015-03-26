package implementation;

import interfaces.DAO;
import interfaces.ShowsManager;

import java.util.ArrayList;
import java.util.List;

import model.Auditorium;
import model.Show;
import model.User;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidUserRoleException;

class DbShowsManager extends DbComponentManager<Show> implements ShowsManager {

	private DAO<Auditorium> auditoriumsDAO;

	public DbShowsManager(User user) {
		super(user, Show.class);
		this.auditoriumsDAO = new DbDAO<>(Auditorium.class);
	}

	public List<Show> getAllManaged() throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		List<Show> allShows = this.componentDAO.readAll();
		List<Show> managedShows = new ArrayList<Show>();

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage shows");

		case Administrator:
			managedShows = allShows;
			break;

		case CinemaManager:
			List<Auditorium> allAuditoriums = this.auditoriumsDAO.readAll();
			for (Show show : allShows) {
				for (Auditorium auditorium : allAuditoriums) {
					if (show.getAuditoriumId().equals(auditorium.getId())
							&& auditorium.getCinemaId().equals(
									this.authenticatedUser.getCinemaId())) {
						managedShows.add(show);
					}
				}
			}
			break;

		default:
			throw new InvalidUserRoleException();
		}

		return managedShows;
	}

	public int addManaged(Show t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage shows");

		case Administrator:
			return this.componentDAO.create(t);

		case CinemaManager:
			this.componentDAO.createForeign(t);
			if (t.getAuditorium().getCinemaId()
					.equals(this.authenticatedUser.getCinemaId())) {
				return this.componentDAO.create(t);
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema shows");
			}

		default:
			throw new InvalidUserRoleException();
		}

	}

	public void deleteManaged(Show t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage shows");

		case Administrator:
			this.componentDAO.delete(t.getId());
			break;

		case CinemaManager:
			this.componentDAO.createForeign(t);
			if (t.getAuditorium().getCinemaId()
					.equals(this.authenticatedUser.getCinemaId())) {
				this.componentDAO.delete(t.getId());
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema shows");
			}
			break;

		default:
			throw new InvalidUserRoleException();
		}

	}

	public void editManaged(Show newT) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage shows");

		case Administrator:
			this.componentDAO.update(newT);
			break;

		case CinemaManager:
			this.componentDAO.createForeign(newT);
			if (this.componentDAO.read(newT.getId()).getAuditorium()
					.getCinemaId().equals(this.authenticatedUser.getCinemaId())
					&& newT.getAuditorium().getCinemaId()
							.equals(this.authenticatedUser.getCinemaId())) {
				this.componentDAO.update(newT);
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema shows");
			}
			break;

		default:
			throw new InvalidUserRoleException();
		}

	}

}
