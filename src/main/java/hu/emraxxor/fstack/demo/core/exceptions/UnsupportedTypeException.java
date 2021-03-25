package hu.emraxxor.fstack.demo.core.exceptions;

public class UnsupportedTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3730412855929818155L;

	public UnsupportedTypeException() {
		super();
	}

	public UnsupportedTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnsupportedTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedTypeException(String message) {
		super(message);
	}

	public UnsupportedTypeException(Throwable cause) {
		super(cause);
	}

}
