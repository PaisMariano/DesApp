package unq.edu.tpi.desapp.model;

public interface PointsCalculatorStrategy {
    public Integer calculatePoints(Integer amount, User user, Project project, DonationSystem donationSystem);
}
