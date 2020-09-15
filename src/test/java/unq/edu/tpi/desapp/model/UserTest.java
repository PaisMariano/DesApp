package unq.edu.tpi.desapp.model;

import org.junit.Test;
import unq.edu.tpi.desapp.model.builders.UserBuilder;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void usersAreCreatedWith0PointsInitially() {
        User newUser = UserBuilder.aUser().build();
        assertEquals((Integer)0, newUser.getPoints());
    }

    @Test
    public void add100PointsToUser2Times() throws IntegerMustBePositive {
        User sampleUser = UserBuilder.aUser().build();
        sampleUser.addPoints(100);
        assertEquals((Integer)100, sampleUser.getPoints());
        sampleUser.addPoints(100);
        assertEquals((Integer)200, sampleUser.getPoints());
    }

    @Test(expected = IntegerMustBePositive.class)
    public void cantAddNegativePointsToUser() throws IntegerMustBePositive {
        User sampleUser = UserBuilder.aUser().build();
        sampleUser.addPoints(-10);
    }

    @Test
    public void spend100PointsToUserWith200Points2Times() throws IntegerMustBePositive {
        User sampleUser = UserBuilder.aUser().build();
        sampleUser.setPoints(200);
        sampleUser.spendPoints(100);
        assertEquals((Integer)100, sampleUser.getPoints());
        sampleUser.spendPoints(100);
        assertEquals((Integer)0, sampleUser.getPoints());
    }

    @Test(expected = IntegerMustBePositive.class)
    public void cantSpendNegativePoints() throws IntegerMustBePositive {
        User sampleUser = UserBuilder.aUser().build();
        sampleUser.spendPoints(-25);
    }

}