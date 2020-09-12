package unq.edu.tpi.desapp.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class SecondColaborationStrategy implements PointsCalculatorStrategy {

    @Override
    public Integer calculatePoints(Integer amount, User user, Project project, ArrayList<Donation> donations) {
        LocalDate aMonthAgo = LocalDate.now().minusMonths(1);
        Integer points = 0;
        Integer ocurrencies = 0;
        for (Donation donation : donations) {
            if (donation.getDate().isAfter(aMonthAgo))
                ocurrencies += 1;
        }
        if (ocurrencies >= 2)
            points = 500;

        return points;
    }
}
