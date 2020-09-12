package unq.edu.tpi.desapp.model;

import java.util.ArrayList;

public interface PointsCalculatorStrategy {
    public Integer calculatePoints(Integer amount, User user, Project project, ArrayList<Donation> donationSystem);
}
