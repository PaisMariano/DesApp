package unq.edu.tpi.desapp.webservices.exceptions;

public class DonationNotFoundException extends Exception {
    private String donation;

    public static DonationNotFoundException createWith(String donation) {
        return new DonationNotFoundException(donation);
    }

    private DonationNotFoundException(String donation) {
        this.donation = donation;
    }

    @Override
    public String getMessage() {
        return "User '" + donation + "' not found";
    }
}