package unq.edu.tpi.desapp.model;

import org.junit.Before;
import org.junit.Test;
import unq.edu.tpi.desapp.model.builders.DonationBuilder;
import unq.edu.tpi.desapp.model.builders.ProjectBuilder;
import unq.edu.tpi.desapp.model.builders.UserBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SecondColaborationStrategyTest {
    SecondColaborationStrategy strategy;
    User user;
    Project project;
    Donation donation1;
    Donation donation2;
    Donation oldDonation1;
    Donation oldDonation2;
    ArrayList<Donation> donations;
    ArrayList<Donation> oldDonations;

    @Before
    public void setUp() {
        strategy = new SecondColaborationStrategy();
        user = UserBuilder.aUser().build();
        project = ProjectBuilder.aProject().build();

        donation1 = DonationBuilder.aDonation().build();
        donation2 = DonationBuilder.aDonation().build();

        oldDonation1 = DonationBuilder.aDonation()
                .withDate(LocalDate.now().minusYears(2))
                .build();
        oldDonation2 = DonationBuilder.aDonation()
                .withDate(LocalDate.now().minusYears(2))
                .build();

        donations = new ArrayList<>();
        donations.add(donation1);
        donations.add(donation2);

        oldDonations = new ArrayList<>();
        oldDonations.add(oldDonation1);
        oldDonations.add(oldDonation2);
    }

    @Test
    public void shouldReturnFiveHundrerPointsWhenTheDonationsAreEqualOrMoreThanTwo(){
        assertEquals(strategy.calculatePoints(2000, user, project, donations), Integer.valueOf(500));
    }

    @Test
    public void shouldReturnZeroPointsWhenTheDonationsAreLesserThanTwo(){
        donations.remove(donation1);
        assertEquals(strategy.calculatePoints(100, user, project, donations), Integer.valueOf(0));
    }

    @Test
    public void shouldReturnZeroPointsWhenDonationsDateAreOlderThanAMonth(){
        assertEquals(strategy.calculatePoints(100, user, project, oldDonations), Integer.valueOf(0));
    }
}