package unq.edu.tpi.desapp.model;

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
}
