package implementation;

import interfaces.AdministrativeModule;
import interfaces.AdministrativeStatistics;
import interfaces.DAO;
import model.Cinema;
import model.User;

class AdministrativeModuleImpl extends CinemaManagerModuleImpl implements
		AdministrativeModule {

	private DAO<Cinema> cinemasManager;
	private AdministrativeStatistics administrativeStatistics;

	public AdministrativeModuleImpl(User user) {
		super(user);
		this.cinemasManager = new DbDAO<>(Cinema.class);
		 this.administrativeStatistics = new DbAdministrativeStatistics();
		 this.cinemaStatistics = null;
	}

	@Override
	public DAO<Cinema> getCinemasManager() {
		return cinemasManager;
	}

	@Override
	public AdministrativeStatistics getAdministrativeStatistics() {
		return administrativeStatistics;
	}

}
