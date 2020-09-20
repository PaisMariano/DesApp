package unq.edu.tpi.desapp.model;

import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

import java.util.ArrayList;

public class User {
    private String username;
    private String email;
    private String password;
    private String nickname;
    private Integer points;
    private ArrayList<Donation> donations;

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

    public ArrayList<Donation> getDonations() {
        return donations;
    }

    public void addDonation(Donation donation) {
        this.donations.add(donation);
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
