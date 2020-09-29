package unq.edu.tpi.desapp.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer amount;
    private String comment;
    private LocalDate date;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "project_id")
    private Project project;

    public Donation() {super();}

    public Donation(Integer amount, String comment, LocalDate date, User user, Project project) {
        this.amount = amount;
        this.comment = comment;
        this.user = user;
        this.project = project;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer calculatePoints() {
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
