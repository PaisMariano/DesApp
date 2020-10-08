package unq.edu.tpi.desapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import unq.edu.tpi.desapp.model.exceptions.EndDateMustBeAfterStartDate;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;
import unq.edu.tpi.desapp.model.exceptions.InvalidFactor;
import unq.edu.tpi.desapp.model.exceptions.InvalidMinClosePercentage;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer factor;
    private Float minClosePercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer raisedFunds;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private ProjectState projectState;

    @OneToMany(mappedBy = "project")
    private List<Donation> donations;

    @ManyToMany
    private Collection<User> users;

    public Project() {super();}

    public Project(String name, Integer factor, Float percentage, LocalDate startDate, LocalDate endDate, Location location, ProjectState projectState) throws EndDateMustBeAfterStartDate, InvalidFactor, InvalidMinClosePercentage {
        this.name = name;
        this.factor = factor;
        this.minClosePercentage = percentage;
        this.endDate = endDate;
        this.location = location;
        this.startDate = startDate;
        this.raisedFunds = 0;
        this.users = new HashSet<>();
        this.donations = new ArrayList<>();
        this.projectState = projectState;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFactor() {
        return factor;
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

    public Integer getRaisedFunds() {
        return raisedFunds;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> participants) {
        this.users = participants;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public void addParticipant(User newParticipant) {
        this.users.add(newParticipant);
    }

    public void addDonation(Donation newDonation) {
        this.donations.add(newDonation);
    }

    public String getState() { return this.getProjectState().getState(); }

    public void setEndDate(LocalDate endDate) throws EndDateMustBeAfterStartDate {
        if (endDate.isBefore(startDate)) {
            throw new EndDateMustBeAfterStartDate();
        }
        this.endDate = endDate;
    }

    public void setRaisedFunds(Integer raisedFunds) throws IntegerMustBePositive {
        if (raisedFunds < 0) {
            throw new IntegerMustBePositive();
        }
        this.raisedFunds = raisedFunds;
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
        return this.getUsers().size();
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
