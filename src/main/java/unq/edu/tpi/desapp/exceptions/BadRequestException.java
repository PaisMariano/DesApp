package unq.edu.tpi.desapp.exceptions;

public class BadRequestException extends Exception {
    private String err;

    public static BadRequestException createWith(String ex) {
        return new BadRequestException(ex);
    }

    private BadRequestException(String ex) {
        this.err = ex;
    }

    @Override
    public String getMessage() {
        return err;
    }
}