package unq.edu.tpi.desapp.webservices.exceptions;

import java.util.List;

public class ApiError {
    private String error;

    public ApiError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}