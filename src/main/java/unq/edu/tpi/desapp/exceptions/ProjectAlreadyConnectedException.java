package unq.edu.tpi.desapp.exceptions;

public class ProjectAlreadyConnectedException extends Exception {
    private String err;

    public ProjectAlreadyConnectedException(String msg) {
        this.err = msg;
    }

    @Override
    public String getMessage() {
        return err;
    }
}