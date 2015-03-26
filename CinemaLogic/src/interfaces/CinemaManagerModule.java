package interfaces;

/**
 * The Interface CinemaManagerModule. Contains cinema manager components.
 */
public interface CinemaManagerModule {

	/**
	 * Gets the users manager component.
	 * 
	 * @return the users manager component
	 */
	public UsersManager getUsersManager();

	/**
	 * Gets the shows manager component.
	 * 
	 * @return the shows manager component
	 */
	public ShowsManager getShowsManager();

	/**
	 * Gets the auditoriums manager component.
	 * 
	 * @return the auditoriums manager component
	 */
	public AuditoriumsManager getAuditoriumsManager();

	/**
	 * Gets the seats manager component.
	 * 
	 * @return the seats manager component
	 */
	public SeatsManager getSeatsManager();

	/**
	 * Gets the cinema statistics component.
	 * 
	 * @return the cinema statistics component
	 */
	public ManagerStatistics getManagerStatistics();
}
