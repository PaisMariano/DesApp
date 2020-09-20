package unq.edu.tpi.desapp.model.exceptions;

public class InvalidFactor extends Exception {
    public InvalidFactor() { super("Invalid factor provided. Project's factor should be between 0 and 100000."); }
}
