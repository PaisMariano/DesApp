package unq.edu.tpi.desapp.model;

import org.junit.Before;
import org.junit.Test;
import unq.edu.tpi.desapp.model.builders.DonationSystemBuilder;
import unq.edu.tpi.desapp.model.builders.LocationBuilder;
import unq.edu.tpi.desapp.model.builders.ProjectBuilder;
import unq.edu.tpi.desapp.model.builders.UserBuilder;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DoubleAmountStrategyTest {
    DoubleAmountStrategy strategy;
    User user;
    Project project1;
    Project project2;
    ArrayList<Donation> donations;
    Location location1;
    Location location2;

    @Before
    public void setUp() {
        strategy = new DoubleAmountStrategy();
        user = UserBuilder.aUser().build();

        location1 = LocationBuilder.aLocation().build();
        location2 = LocationBuilder.aLocation()
                .withPopulation(3000)
                .build();

        project1 = ProjectBuilder.aProject().build();
        project2 = ProjectBuilder.aProject()
                .withLocation(location2)
                .build();
        donations = new ArrayList<>();
    }

    @Test
    public void shouldReturnFourThousandPointsWithLocationPopulationLesserThanTwoThousand(){
        assertEquals(strategy.calculatePoints(2000, user, project1, donations), Integer.valueOf(4000));
    }

    @Test
    public void shouldReturnZeroWithLocationPopulationGreaterOrEqualToTwoThousand(){
        assertEquals(strategy.calculatePoints(2000, user, project2, donations), Integer.valueOf(0));
    }
}