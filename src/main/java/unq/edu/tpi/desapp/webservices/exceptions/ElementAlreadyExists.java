package unq.edu.tpi.desapp.webservices.exceptions;

public class ElementAlreadyExists extends Exception {
    private String err;

    public static ElementAlreadyExists createWith() {
        return new ElementAlreadyExists();
    }

    private ElementAlreadyExists() {
        this.err = "Cannot create element, it already exists.";
    }

    @Override
    public String getMessage() {
        return err;
    }
}