package unq.edu.tpi.desapp.model;

import org.springframework.stereotype.Component;

@Component
public class ArsatLocation {
    private String name;
    private String province;
    private Integer population;

    public ArsatLocation() {};

    public ArsatLocation(String name, String province, Integer population) {
        this.name = name;
        this.province = province;
        this.population = population;
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

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
