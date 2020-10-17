package unq.edu.tpi.desapp.webservices.exceptions;

public class ElementAlreadyExists extends Exception {
    private String err;

    public ElementAlreadyExists(String msg) {
        this.err = msg;
    }

    @Override
    public String getMessage() {
        return err;
    }
}