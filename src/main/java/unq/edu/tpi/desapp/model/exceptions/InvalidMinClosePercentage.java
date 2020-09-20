package unq.edu.tpi.desapp.model.exceptions;

public class InvalidMinClosePercentage extends Exception {
    public InvalidMinClosePercentage() { super("Invalid minimum close percentage provided. Project's minimum close percentage should be between 50.0 and 100.0."); }
}
