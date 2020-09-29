package unq.edu.tpi.desapp.model;

import unq.edu.tpi.desapp.model.exceptions.EndDateMustBeAfterStartDate;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;
import unq.edu.tpi.desapp.model.exceptions.InvalidFactor;
import unq.edu.tpi.desapp.model.exceptions.InvalidMinClosePercentage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Project {
    private String name;
    private Integer factor;
    private Float minClosePercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer raisedFunds;
    private Location location;
    private Collection<User> participants;
    private Collection<Donation> donations;
    private ProjectState projectState;

    public Project() {super();}

    public Project(String name, Integer factor, Float percentage, LocalDate startDate, LocalDate endDate, Location location) throws EndDateMustBeAfterStartDate, InvalidFactor, InvalidMinClosePercentage {
        this.name = name;
        this.factor = factor;
        this.minClosePercentage = percentage;
        this.endDate = endDate;
        this.location = location;
        this.startDate = startDate;
        this.raisedFunds = 0;
        this.participants = new HashSet<>();
        this.donations = new ArrayList<>();
        this.projectState = new Planned();
        if (factor < 0 || factor > 100000) {
            throw new InvalidFactor();
        }
        if (percentage < 50.0 || percentage > 100.0) {
            throw new InvalidMinClosePercentage();
        }
        if (endDate.isBefore(startDate)) {
            throw new EndDateMustBeAfterStartDate();
        }
    }

    public Collection<User> getParticipants() {
        return participants;
    }

    public Collection<Donation> getDonations() {
        return donations;
    }

    public void addParticipant(User newParticipant) {
        this.participants.add(newParticipant);
    }

    public void addDonation(Donation newDonation) {
        this.donations.add(newDonation);
    }

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

    public void setEndDate(LocalDate endDate) throws EndDateMustBeAfterStartDate {
        if (endDate.isBefore(startDate)) {
            throw new EndDateMustBeAfterStartDate();
        }
        this.endDate = endDate;
    }

    public Integer getRaisedFunds() {
        return raisedFunds;
    }

    public void setRaisedFunds(Integer raisedFunds) throws IntegerMustBePositive {
        if (raisedFunds < 0) {
            throw new IntegerMustBePositive();
        }
        this.raisedFunds = raisedFunds;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public String getState() { return this.getProjectState().getState(); }

    public Integer getFactor() {
        return factor;
    }

    public void setFactor(Integer factor) throws InvalidFactor {
        if (factor < 0 || factor > 100000) {
            throw new InvalidFactor();
        }
        this.factor = factor;
    }

    public Float getMinClosePercentage() {
        return minClosePercentage;
    }

    public void setMinClosePercentage(Float minClosePercentage) throws InvalidMinClosePercentage {
        if (minClosePercentage < 50.0 || minClosePercentage > 100.0) {
            throw new InvalidMinClosePercentage();
        }
        this.minClosePercentage = minClosePercentage;
    }

    public void addFunds(Integer givenFunds) throws IntegerMustBePositive {
        if (givenFunds < 0) {
            throw new IntegerMustBePositive();
        }
        raisedFunds += givenFunds;
    }

    public Float accumulatedValuePercentage() {
        return getRaisedFunds() / maxFundsRequired() * 100;
    }

    public Float missingPercentageToComplete() {
        return 100 - (getRaisedFunds() / totalNecessaryValue() * 100);
    }

    public Integer participantsAmount() {
        return this.getParticipants().size();
    }

    private Float maxFundsRequired() {
        return Float.valueOf(getLocation().getPopulation() * getFactor());
    }

    public Float totalNecessaryValue() {
        return maxFundsRequired() * getMinClosePercentage() / 100;
    }

    public void donate(Integer amount, String comment, User user) throws IntegerMustBePositive, EndDateMustBeAfterStartDate, InvalidMinClosePercentage, InvalidFactor {
        this.getProjectState().donate(amount, comment, user, this);
    }

    public void completeProject() {
        this.getProjectState().complete(this);
    }
}
