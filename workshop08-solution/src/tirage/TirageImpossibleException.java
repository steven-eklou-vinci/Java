package tirage;

public class TirageImpossibleException extends RuntimeException {

	public TirageImpossibleException(String message) {
		super(message);
	}

	public TirageImpossibleException(Throwable cause) {
		super(cause);
	}

	public TirageImpossibleException(String message, Throwable cause) {
		super(message, cause);
	}

	public TirageImpossibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
