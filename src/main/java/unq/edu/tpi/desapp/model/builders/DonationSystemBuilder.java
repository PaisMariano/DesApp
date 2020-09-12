package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.*;

import java.util.ArrayList;

public class DonationSystemBuilder {
    private ArrayList<PointsCalculatorStrategy> strategies = new ArrayList<>();

    public static DonationSystemBuilder aDonationSystem(){
        return new DonationSystemBuilder();
    }
    
    public DonationSystem build() {
        return new DonationSystem(strategies);
    }
}
