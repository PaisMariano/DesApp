package unq.edu.tpi.desapp.model.exceptions;

public class EndDateMustBeAfterStartDate extends Exception {
    public EndDateMustBeAfterStartDate() {
        super("Invalid end date provided. Must be after Project's start date.");
    }
}
