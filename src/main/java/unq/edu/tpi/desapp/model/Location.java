package unq.edu.tpi.desapp.model;

import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

public class Location {
    private String name;
    private String province;
    private Integer population;

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

    public void setPopulation(Integer population) throws IntegerMustBePositive {
        if (population < 1) {
            throw new IntegerMustBePositive("Invalid population input. Must be greater or equal than 0.");
        }
        this.population = population;
    }

    public Location(String name, String province, Integer population) throws IntegerMustBePositive {
        this.name = name;
        this.province = province;
        this.population = population;
        if (population < 1) {
            throw new IntegerMustBePositive("Invalid population input. Must be greater or equal than 0.");
        }
    }

    public Integer getPopulation() {
        return population;
    }
}
