package unq.edu.tpi.desapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Project {
    private String name;
    private Integer factor;
    private Double minClosePercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer raisedFunds;
    private Location location;
    private Collection<User> participants;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getRaisedFunds() {
        return raisedFunds;
    }

    public void setRaisedFunds(Integer raisedFunds) {
        this.raisedFunds = raisedFunds;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

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
        raisedFunds += givenFunds;
    }

    public Float accumulatedValuePercentage() {
        return 0.5f;
    }

    public Integer totalNecessaryValue() {
        return 6;
    }
}
