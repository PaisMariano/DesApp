package unq.edu.tpi.desapp.model;

public class Project {
    private Integer funds;
    private Location location;

    public void addFunds(Integer givenFunds) {
        funds = funds + givenFunds;
    }


    public Location getLocation() {
        return location;
    }
}
