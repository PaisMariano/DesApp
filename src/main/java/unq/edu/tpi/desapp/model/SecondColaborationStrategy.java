package unq.edu.tpi.desapp.model;

import java.time.LocalDate;

public class SecondColaborationStrategy implements PointsCalculatorStrategy {

    @Override
    public Integer calculatePoints(Integer amount, User user, Project project, DonationSystem donationSystem) {
        LocalDate aMonthAgo = LocalDate.now().minusMonths(1);
        Integer points = 0;
        Integer ocurrencies = 0;
        for (Donation donation : donationSystem.getDonationsByUser(user.getUsername())) {
            if (aMonthAgo.isAfter(donation.getDate()))
                ocurrencies += 1;
        }
        if (ocurrencies >= 2)
            points = 500;

        return points;
    }
}
