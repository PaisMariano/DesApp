package unq.edu.tpi.desapp.model;

import java.util.ArrayList;

public class DoubleAmountStrategy implements PointsCalculatorStrategy {
    @Override
    public Integer calculatePoints(Integer amount, User user, Project project, ArrayList<Donation> donations) {
        Integer points = 0;
        if (project.getLocation().getPopulation() < 2000)
            points = amount * 2;
        return points;
    }
}
