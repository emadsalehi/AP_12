package model.exceptions;

public class NotPossibleException extends RuntimeException {
    public NotPossibleException (String error) {
        super(error);
    }
}
