package implementation;

import interfaces.MoviesManager;

import java.util.List;

import model.Movie;
import model.User;
import exceptions.FailedDatabaseOperationException;
import exceptions.InvalidUserRoleException;

class DbMoviesManager extends DbComponentManager<Movie> implements
		MoviesManager {

	public DbMoviesManager(User user) {
		super(user, Movie.class);
	}

	@Override
	public List<Movie> getAllManaged() throws FailedDatabaseOperationException {
		return this.componentDAO.readAll();
	}

	@Override
	public int addManaged(Movie t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
		case CinemaManager:
			throw new InvalidUserRoleException(
					"Only administrator can manage movies");

		case Administrator:
			return this.componentDAO.create(t);

		default:
			throw new InvalidUserRoleException();
		}

	}

	@Override
	public void deleteManaged(Movie t) throws InvalidUserRoleException,
			FailedDatabaseOperationException {
		switch (this.authenticatedUser.getRole()) {

		case Cashier:
		case CinemaManager:
			throw new InvalidUserRoleException(
					"Only administrator can manage movies");

		case Administrator:
			this.componentDAO.delete(t.getId());
			break;

		default:
			throw new InvalidUserRoleException();
		}
	}

	@Override
	public void editManaged(Movie newT) throws InvalidUserRoleException,
			FailedDatabaseOperationException {

		switch (this.authenticatedUser.getRole()) {

		case Cashier:
		case CinemaManager:
			throw new InvalidUserRoleException(
					"Only administrator can manage movies");

		case Administrator:
			this.componentDAO.update(newT);
			break;

		default:
			throw new InvalidUserRoleException();
		}

	}

}
