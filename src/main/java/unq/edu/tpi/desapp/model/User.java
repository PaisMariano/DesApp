package unq.edu.tpi.desapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import unq.edu.tpi.desapp.model.exceptions.BadEmailAddressException;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Size(min = 3, max = 16, message = "Username must have between 3 and 16 letters")
    private String username;
    private String email;
    @JsonIgnore
    @Size(min = 3, max = 16, message = "Nickname must have between 3 and 16 letters")
    private String password;
    @Size(min = 3, max = 16, message = "Nickname must have between 3 and 16 letters")
    private String nickname;
    private Integer points;

    @OneToMany(mappedBy = "user")
    private List<Donation> donations;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Project> projects;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<Project> userProjects;

    public User() {super();}

    public User(String username,
                String email,
                String password,
                String nickname,
                ArrayList<Donation> donations) throws BadEmailAddressException {
        this.username = username;
        this.email = validateEmail(email);
        this.password = password;
        this.nickname = nickname;
        this.points = 0;
        this.donations = donations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    private String validateEmail(String email) throws BadEmailAddressException {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            throw new BadEmailAddressException();

        return email;
    }
}
