package unq.edu.tpi.desapp.model;

import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String username;
    private String email;
    private String password;
    private String nickname;
    private Integer points;

    @OneToMany(mappedBy = "user")
    private List<Donation> donations;

    @ManyToMany
    private List<Project> projects;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Project> userProjects;

    public User() {super();}

    public User(String username, String email, String password, String nickname, ArrayList<Donation> donations){
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.points = 0;
        this.donations = donations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void addDonation(Donation donation) {
        this.donations.add(donation);
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<Project> userProjects) {
        this.userProjects = userProjects;
    }

    public void addPoints(Integer givenPoints) throws IntegerMustBePositive {
        if (givenPoints < 0) {
            throw new IntegerMustBePositive();
        }
        points += givenPoints;
    }

    public void spendPoints(Integer pointsToSpend) throws IntegerMustBePositive {
        if (pointsToSpend < 0) {
            throw new IntegerMustBePositive();
        }
        points -= pointsToSpend;
    }

}
