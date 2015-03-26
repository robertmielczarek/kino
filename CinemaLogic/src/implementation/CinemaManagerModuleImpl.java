package implementation;

import interfaces.AuditoriumsManager;
import interfaces.CinemaManagerModule;
import interfaces.ManagerStatistics;
import interfaces.SeatsManager;
import interfaces.ShowsManager;
import interfaces.UsersManager;
import model.User;

class CinemaManagerModuleImpl implements CinemaManagerModule {

	private UsersManager usersManager;
	private ShowsManager showsManager;
	private AuditoriumsManager auditoriumsManager;
	private SeatsManager seatsManager;
	protected ManagerStatistics cinemaStatistics;

	public CinemaManagerModuleImpl(User user) {

		this.usersManager = new DbUsersManager(user);
		this.showsManager = new DbShowsManager(user);
		this.auditoriumsManager = new DbAuditoriumsManager(user);
		this.seatsManager = new DbSeatsManager(user);
		this.cinemaStatistics = new DbManagerStatistics(user);
	}

	@Override
	public UsersManager getUsersManager() {
		return usersManager;
	}

	@Override
	public ShowsManager getShowsManager() {
		return showsManager;
	}

	@Override
	public AuditoriumsManager getAuditoriumsManager() {
		return auditoriumsManager;
	}

	@Override
	public SeatsManager getSeatsManager() {
		return seatsManager;
	}

	@Override
	public ManagerStatistics getManagerStatistics() {
		return cinemaStatistics;
	}

}
