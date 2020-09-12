package unq.edu.tpi.desapp.model;

public class Location {
    private String name;
    private String province;
    private Integer population;

    public Location(String name, String province, Integer population) {
        this.name = name;
        this.province = province;
        this.population = population;
    }

    public Integer getPopulation() {
        return population;
    }
}
