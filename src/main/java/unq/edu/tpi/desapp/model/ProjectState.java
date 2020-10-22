package unq.edu.tpi.desapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import unq.edu.tpi.desapp.model.exceptions.EndDateMustBeAfterStartDate;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;
import unq.edu.tpi.desapp.model.exceptions.InvalidFactor;
import unq.edu.tpi.desapp.model.exceptions.InvalidMinClosePercentage;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project_state")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ProjectState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    protected Integer id;

    protected String state;

    @OneToMany(mappedBy = "project")
    private List<Donation> donations;

    @OneToMany(mappedBy = "projectState")
    @JsonIgnore
    protected List<Project> projects;

    public ProjectState() {}

    public ProjectState(String state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Donation> getDonations() {
        return donations;
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

    public String getState() {
        return state;
    }

    public void complete(Project aProject) {
        // reusable no behaviour method
    }
    public void donate(Donation donation, User user, Project project) throws IntegerMustBePositive, EndDateMustBeAfterStartDate, InvalidMinClosePercentage, InvalidFactor {
        // reusable no behaviour method
    }
}
