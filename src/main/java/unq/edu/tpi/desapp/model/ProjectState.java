package unq.edu.tpi.desapp.model;

import unq.edu.tpi.desapp.model.exceptions.EndDateMustBeAfterStartDate;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;
import unq.edu.tpi.desapp.model.exceptions.InvalidFactor;
import unq.edu.tpi.desapp.model.exceptions.InvalidMinClosePercentage;

import javax.persistence.*;

@Entity
@Table(name = "project_state")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ProjectState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    protected Integer id;

    protected String state;

    @OneToOne(mappedBy = "projectState")
    protected Project project;

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getState() {
        return state;
    }

    public void complete(Project aProject) {
        // reusable no behaviour method
    }
    public void donate(Integer amount, String comment, User user, Project project) throws IntegerMustBePositive, EndDateMustBeAfterStartDate, InvalidMinClosePercentage, InvalidFactor {
        // reusable no behaviour method
    }
}
