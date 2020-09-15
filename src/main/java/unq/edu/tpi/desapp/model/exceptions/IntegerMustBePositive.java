package unq.edu.tpi.desapp.model.exceptions;

public class IntegerMustBePositive extends Exception {
    public IntegerMustBePositive() {
        super("Invalid integer value provided. Must be a positive value.");
    }
    public IntegerMustBePositive(String errorMessage) {
        super(errorMessage);
    }
}