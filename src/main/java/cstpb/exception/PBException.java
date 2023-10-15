package cstpb.exception;

public class PBException extends RuntimeException{

    public PBException(String message) {
        super(message);
    }

    public PBException(String message, Throwable cause) {
        super(message, cause);
    }

    public PBException(Throwable cause) {
        super(cause);
    }
}
