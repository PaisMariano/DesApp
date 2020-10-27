package unq.edu.tpi.desapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "donation")
public class Donation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer amount;
    private String comment;
    private LocalDate date;
    private String userNickname;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne()
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    public Donation() {super();}

    public Donation(Integer amount, String comment, LocalDate date, String userNickname) throws IntegerMustBePositive {
        if (amount < 0) {
            throw new IntegerMustBePositive();
        }

        this.amount = amount;
        this.comment = comment;
        this.date = date;
        this.userNickname = userNickname;
    }

    public Donation(Integer amount, String comment, LocalDate date, User user, Project project) throws IntegerMustBePositive {
        if (amount < 0) {
            throw new IntegerMustBePositive();
        }

        this.amount = amount;
        this.comment = comment;
        this.user = user;
        this.project = project;
        this.date = date;
        this.userNickname = user.getNickname();
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

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Integer calculatePoints() {
        return calculateSameAmount() + calculateDouble() + calculateSecondColaboration();
    }

    public void setAmountWithException(Integer amount) throws IntegerMustBePositive {
        if (this.amount < 0) {
            throw new IntegerMustBePositive();
        }

        this.amount = amount;
    }

    public void calculateUserPoints() throws IntegerMustBePositive {
        this.user.addPoints(this.calculatePoints());
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
