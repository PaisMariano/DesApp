package unq.edu.tpi.desapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String province;
    private Integer population;

    @OneToOne(fetch =FetchType.LAZY, mappedBy = "location")
    @JsonIgnore
    private Project project;

    public Location() {super();}

    public Location(String name, String province, Integer population) throws IntegerMustBePositive {
        this.name = name;
        this.province = province;
        this.population = population;
        if (population < 1) {
            throw new IntegerMustBePositive("Invalid population input. Must be greater or equal than 0.");
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) throws IntegerMustBePositive {
        if (population < 1) {
            throw new IntegerMustBePositive("Invalid population input. Must be greater or equal than 0.");
        }
        this.population = population;
    }


}
