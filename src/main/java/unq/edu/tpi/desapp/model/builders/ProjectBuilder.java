package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.Location;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.model.exceptions.EndDateMustBeAfterStartDate;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;
import unq.edu.tpi.desapp.model.exceptions.InvalidFactor;
import unq.edu.tpi.desapp.model.exceptions.InvalidMinClosePercentage;

import java.time.LocalDate;

public class ProjectBuilder {
    private String name = "noName";
    private Integer factor = 1000;
    private Float minClosePercentage = 100.0f;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.of(2055, 12, 22);
    private Location location = LocationBuilder.aLocation().build();

    public ProjectBuilder() throws IntegerMustBePositive {}

    public static ProjectBuilder aProject() throws IntegerMustBePositive {
        return new ProjectBuilder();
    }

    public Project build() throws EndDateMustBeAfterStartDate, InvalidMinClosePercentage, InvalidFactor {
        return new Project(name, factor, minClosePercentage, startDate, endDate, location);
    }

    public ProjectBuilder withLocation(Location givenLocation) {
        location = givenLocation;
        return this;
    }

    public ProjectBuilder withEndDate(LocalDate givenDate) {
        endDate = givenDate;
        return this;
    }

    public ProjectBuilder withFactor(Integer givenFactor) {
        factor = givenFactor;
        return this;
    }

    public ProjectBuilder withMinClosePercentage(Float givenPercentage) {
        minClosePercentage = givenPercentage;
        return this;
    }

    public ProjectBuilder withStartDate(LocalDate givenDate) {
        startDate = givenDate;
        return this;
    }
}
