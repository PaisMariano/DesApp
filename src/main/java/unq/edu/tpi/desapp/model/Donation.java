package unq.edu.tpi.desapp.model;

import jdk.vm.ci.meta.Local;

import java.time.LocalDate;

public class Donation {
    private Integer amount;
    private String comment;
    private LocalDate date;
    private User user;
    private Project project;

    public Donation(Integer amount, String comment, User user, Project project) {
        amount = amount;
        comment = comment;
        user = user;
        project = project;
        date = LocalDate.now();
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
