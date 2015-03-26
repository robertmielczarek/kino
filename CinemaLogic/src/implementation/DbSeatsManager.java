package implementation;

import java.util.ArrayList;
import java.util.List;

import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidUserRoleException;
import interfaces.DAO;
import interfaces.SeatsManager;
import model.Auditorium;
import model.Seat;
import model.User;

class DbSeatsManager extends DbComponentManager<Seat> implements SeatsManager {

	private DAO<Auditorium> auditoriumDAO;

	public DbSeatsManager(User user) {
		super(user, Seat.class);
		this.auditoriumDAO = new DbDAO<>(Auditorium.class);
	}

	@Override
	public List<Seat> getAllManaged() throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		List<Seat> managedSeats = new ArrayList<>();
		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage seats");

		case Administrator:
			managedSeats = this.componentDAO.readAll();
			break;

		case CinemaManager:
			List<Seat> allSeats = this.componentDAO.readAll();
			List<Auditorium> allAuditoriums = this.auditoriumDAO.readAll();

			for (Seat seat : allSeats) {
				for (Auditorium auditorium : allAuditoriums) {
					if (seat.getAuditoriumId().equals(auditorium.getId())
							&& auditorium.getCinemaId().equals(
									this.authenticatedUser.getCinemaId())) {
						managedSeats.add(seat);
					}
				}
			}
			break;

		default:
			throw new InvalidUserRoleException();
		}

		return managedSeats;
	}

	@Override
	public int addManaged(Seat t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage seats");

		case Administrator:
			return this.componentDAO.create(t);

		case CinemaManager:
			this.componentDAO.createForeign(t);
			if (t.getAuditorium().getCinemaId()
					.equals(this.authenticatedUser.getCinemaId())) {
				return this.componentDAO.create(t);
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema seat");
			}

		default:
			throw new InvalidUserRoleException();
		}
	}

	@Override
	public void deleteManaged(Seat t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage seats");

		case Administrator:
			this.componentDAO.delete(t.getId());
			break;

		case CinemaManager:
			this.componentDAO.createForeign(t);
			if (t.getAuditorium().getCinemaId()
					.equals(this.authenticatedUser.getCinemaId())) {
				this.componentDAO.delete(t.getId());
				break;
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema seat");
			}

		default:
			throw new InvalidUserRoleException();
		}
	}

	@Override
	public void editManaged(Seat newT) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
			throw new InvalidUserRoleException("Cashier cannot manage seats");

		case Administrator:
			this.componentDAO.update(newT);
			break;

		case CinemaManager:
			this.componentDAO.createForeign(newT);
			if (newT.getAuditorium().getCinemaId()
					.equals(this.authenticatedUser.getCinemaId())) {
				this.componentDAO.update(newT);
				break;
			} else {
				throw new InvalidUserRoleException(
						"Cannot manage foreign cinema seat");
			}

		default:
			throw new InvalidUserRoleException();
		}
	}

}
