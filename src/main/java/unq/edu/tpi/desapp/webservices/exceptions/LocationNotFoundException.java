package unq.edu.tpi.desapp.webservices.exceptions;

public class LocationNotFoundException extends Exception {
    private String locationName;

    public static LocationNotFoundException createWith(String locationName) {
        return new LocationNotFoundException(locationName);
    }

    private LocationNotFoundException(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String getMessage() {
        return "Location '" + locationName + "' not found";
    }
}