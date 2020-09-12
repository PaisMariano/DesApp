package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.Location;
import unq.edu.tpi.desapp.model.Project;

import java.time.LocalDate;

public class ProjectBuilder {
    public static ProjectBuilder aProject() {
        return new ProjectBuilder();
    }
    private String name = "noName";
    private Integer factor = 0;
    private Double minClosePercentage = 0.0;
    private LocalDate endDate = LocalDate.now();
    private Location location = LocationBuilder.aLocation().build();

    public Project build() {
        return new Project(name, factor, minClosePercentage, endDate, location);
    }

    //Faltan los withX - EJ withName(final String name) { }
}
