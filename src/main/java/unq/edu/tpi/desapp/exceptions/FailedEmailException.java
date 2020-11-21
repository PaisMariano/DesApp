package unq.edu.tpi.desapp.exceptions;

public class FailedEmailException extends Exception {
    private String err;

    public FailedEmailException(String msg) {
        this.err = msg;
    }

    @Override
    public String getMessage() {
        return err;
    }
}