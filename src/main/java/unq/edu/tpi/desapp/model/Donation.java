package unq.edu.tpi.desapp.model;

import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

import java.time.LocalDate;

public class Donation {
    private Integer amount;
    private String comment;
    private LocalDate date;
    private User user;
    private Project project;

    public Donation(Integer amount, String comment, LocalDate date, User user, Project project) {
        this.amount = amount;
        this.comment = comment;
        this.user = user;
        this.project = project;
        this.date = date;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer calculatePoints() throws IntegerMustBePositive {
        return calculateSameAmount() + calculateDouble() + calculateSecondColaboration();
    }

    public Integer calculateDouble() {
        Integer points = 0;
        if (project.getLocation().getPopulation() < 2000)
            points = amount * 2;
        return points;
    }

    public Integer calculateSameAmount() {
        Integer points = 0;
        if (amount > 1000)
            points = amount;
        return points;
    }

    public Integer calculateSecondColaboration() {
        LocalDate aMonthAgo = LocalDate.now().minusMonths(1);
        Integer points = 0;
        Integer ocurrencies = 0;
        for (Donation donation : user.getDonations()) {
            if (donation.getDate().isAfter(aMonthAgo))
                ocurrencies += 1;
        }
        if (ocurrencies >= 1)
            points = 500;

        return points;
    }
}
