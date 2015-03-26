package implementation;

import interfaces.ComponentManager;
import interfaces.DAO;
import interfaces.RestElement;
import model.User;

abstract class DbComponentManager<T extends RestElement> implements
		ComponentManager<T> {

	User authenticatedUser;
	DAO<T> componentDAO;

	public DbComponentManager(User user, Class<T> parameterType) {
		this.authenticatedUser = user;
		this.componentDAO = new DbDAO<>(parameterType);
	}
}
