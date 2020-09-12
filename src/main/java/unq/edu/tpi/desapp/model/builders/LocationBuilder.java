package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.Location;

public class LocationBuilder {
    public static LocationBuilder aLocation() {
        return new LocationBuilder();
    }
    private String name = "noName";
    private String province = "noProvince";
    private Integer population = 0;

    public Location build() {
        return new Location(name, province, population);
    }

    //Faltan los withX - EJ withName(final String name) { }
}
