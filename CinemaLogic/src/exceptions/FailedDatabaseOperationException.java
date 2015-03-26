package exceptions;

public class FailedDatabaseOperationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FailedDatabaseOperationException() {
		super();
	}

	public FailedDatabaseOperationException(String message) {
		super(message);
	}

}
