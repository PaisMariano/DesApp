package unq.edu.tpi.desapp.model.exceptions;

public class BadEmailAddressException extends Exception{
    public BadEmailAddressException() {
        super("Invalid email provided.");
    }
}
