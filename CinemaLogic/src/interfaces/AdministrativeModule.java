package interfaces;

import model.Cinema;

/**
 * The Interface AdministrativeModule. Contains administrative components.
 */
public interface AdministrativeModule extends CinemaManagerModule {

	/**
	 * Gets the cinemas manager.
	 * 
	 * @return the cinemas manager
	 */
	public DAO<Cinema> getCinemasManager();

	/**
	 * Gets the administrative statistics.
	 * 
	 * @return the administrative statistics
	 */
	public AdministrativeStatistics getAdministrativeStatistics();
}
