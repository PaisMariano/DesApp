package unq.edu.tpi.desapp.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import unq.edu.tpi.desapp.model.builders.DonationSystemBuilder;
import unq.edu.tpi.desapp.model.builders.ProjectBuilder;
import unq.edu.tpi.desapp.model.builders.UserBuilder;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SameAmountStrategyTest {
    SameAmountStrategy strategy;
    User user;
    Project project;
    ArrayList<Donation> donations;

    @Before
    public void setUp() {
        strategy = new SameAmountStrategy();
        user = UserBuilder.aUser().build();
        project = ProjectBuilder.aProject().build();
        donations = new ArrayList<>();
    }

    @Test
    public void shouldReturnTwoThousandPointsWhenTheAmountIsTwoThousand(){
        assertEquals(strategy.calculatePoints(2000, user, project, donations), Integer.valueOf(2000));
    }

    @Test
    public void shouldReturnZeroPointsWhenTheAmountIsLessThanOneThousand(){
        assertEquals(strategy.calculatePoints(500, user, project, donations), Integer.valueOf(0));
    }

}