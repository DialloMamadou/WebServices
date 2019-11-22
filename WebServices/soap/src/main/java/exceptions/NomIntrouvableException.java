package exceptions;

public class NomIntrouvableException extends Exception {

    public NomIntrouvableException() {
    }

    public NomIntrouvableException(String message) {
        super(message);
    }

    public NomIntrouvableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NomIntrouvableException(Throwable cause) {
        super(cause);
    }

    public NomIntrouvableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
