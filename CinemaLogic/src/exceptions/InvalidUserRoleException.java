package exceptions;

public class InvalidUserRoleException extends Exception {

	public InvalidUserRoleException(String message) {
		super(message);
	}

	public InvalidUserRoleException() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
