package unq.edu.tpi.desapp.model;

public class DoubleAmountStrategy implements PointsCalculatorStrategy {
    @Override
    public Integer calculatePoints(Integer amount, User user, Project project, DonationSystem donationSystem) {
        Integer points = 0;
        if (project.getLocation().getPopulation() < 2000)
            points = amount * 2;
        return points;
    }
}
