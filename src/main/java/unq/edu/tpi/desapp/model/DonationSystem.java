package unq.edu.tpi.desapp.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class DonationSystem {
    private ArrayList<PointsCalculatorStrategy> strategies;
    private ArrayList<Donation> donations;

    public DonationSystem(ArrayList<PointsCalculatorStrategy> strategies) {
        this.strategies = strategies;
    }

    public void donate(Integer amount, String comment, User user, Project project) {
        donations.add(new Donation(amount, comment, LocalDate.now(), user, project));
        user.addPoints(calculatePoints(amount, user, project));
        project.addFunds(amount);
    }

    public ArrayList<Donation> getDonationsByUser(String username){
        ArrayList<Donation> tempDonations = new ArrayList<>();
        for (Donation donation : donations) {
            if (donation.getUser().getUsername().equals(username))
                tempDonations.add(donation);
        }
        return tempDonations;
    }

    private Integer calculatePoints(Integer amount, User user, Project project){
        Integer totalPoints = 0;
        for(PointsCalculatorStrategy str : strategies){
            totalPoints = str.calculatePoints(amount, user, project, getDonationsByUser(user.getUsername()));
        }
        return totalPoints;
    }
}
