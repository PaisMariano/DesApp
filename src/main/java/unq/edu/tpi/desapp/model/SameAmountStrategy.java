package unq.edu.tpi.desapp.model;

public class SameAmountStrategy implements PointsCalculatorStrategy {
    @Override
    public Integer calculatePoints(Integer amount, User user, Project project, DonationSystem donationSystem) {
        Integer points = 0;
        if (amount > 1000)
            points = amount;
        return points;
    }
}
