package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.Location;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

public class LocationBuilder {
    private String name = "noName";
    private String province = "noProvince";
    private Integer population = 1;

    public static LocationBuilder aLocation() {
        return new LocationBuilder();
    }

    public Location build() throws IntegerMustBePositive {
        return new Location(name, province, population);
    }

    public LocationBuilder withPopulation(Integer givenPopulation) {
        population = givenPopulation;
        return this;
    }
}
