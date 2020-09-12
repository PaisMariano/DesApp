package unq.edu.tpi.desapp.model;

import jdk.vm.ci.meta.Local;

import java.time.LocalDate;
import java.util.ArrayList;

public class Project {
    private String name;
    private Integer factor;
    private Double minClosePercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer raisedFunds;
    private Location location;
    private ArrayList<User> participants;

    public Project(String name, Integer factor, Double percentage, LocalDate endDate, Location location) {
        this.name = name;
        this.factor = factor;
        this.minClosePercentage = percentage;
        this.endDate = endDate;
        this.location = location;
        this.startDate = LocalDate.now();
        this.raisedFunds = 0;
        this.participants = new ArrayList<>();
    }
    public Location getLocation() {
        return location;
    }

    public void addFunds(Integer givenFunds) {
        raisedFunds = raisedFunds + givenFunds;
    }

}
