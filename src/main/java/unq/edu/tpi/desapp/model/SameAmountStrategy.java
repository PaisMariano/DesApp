package unq.edu.tpi.desapp.model;

import java.util.ArrayList;

public class SameAmountStrategy implements PointsCalculatorStrategy {
    @Override
    public Integer calculatePoints(Integer amount, User user, Project project, ArrayList<Donation> donations) {
        Integer points = 0;
        if (amount > 1000)
            points = amount;
        return points;
    }
}
