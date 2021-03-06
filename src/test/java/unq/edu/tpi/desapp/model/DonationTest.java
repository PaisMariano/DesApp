package unq.edu.tpi.desapp.model;

import org.junit.Test;
import unq.edu.tpi.desapp.model.builders.DonationBuilder;
import unq.edu.tpi.desapp.model.builders.LocationBuilder;
import unq.edu.tpi.desapp.model.builders.ProjectBuilder;
import unq.edu.tpi.desapp.model.builders.UserBuilder;
import unq.edu.tpi.desapp.model.exceptions.*;

import static org.junit.Assert.assertEquals;

public class DonationTest {
    private User userForDonations = UserBuilder.aUser()
            .build();

    private Location location3000 = LocationBuilder.aLocation()
            .withPopulation(3000)
            .build();

    private Project project3000 = ProjectBuilder.aProject()
            .withLocation(location3000)
            .build();

    private Donation donationSameAmount1 = DonationBuilder.aDonation()
            .withAmount(2000)
            .withUser(userForDonations)
            .build();
    private Donation donationSameAmount2 = DonationBuilder.aDonation()
            .withAmount(100)
            .build();

    private Donation donationDoubleAmount1 = DonationBuilder.aDonation()
            .withAmount(2000)
            .withProject(project3000)
            .build();
    private Donation donationDoubleAmount2 = DonationBuilder.aDonation()
            .withAmount(2000)
            .build();

    private Donation donationSecondColab1 = DonationBuilder.aDonation()
            .withAmount(1000)
            .withUser(userForDonations)
            .build();
    private Donation donationSecondColab2 = DonationBuilder.aDonation()
            .withAmount(1000)
            .build();

    public DonationTest() throws EndDateMustBeAfterStartDate, InvalidMinClosePercentage, InvalidFactor, IntegerMustBePositive, BadEmailAddressException {
    }

    @Test
    public void calcDoubleShouldReturnZeroWithLocationPopulationGreaterThanTwoThousand() {
        assertEquals(donationDoubleAmount1.calculateDouble(), Integer.valueOf(0));
    }

    @Test
    public void calcDoubleShouldReturnFourThousandPointsWithLocationPopulationLesserThanTwoThousand(){
        assertEquals(donationDoubleAmount2.calculateDouble(), Integer.valueOf(4000));
    }

    @Test
    public void sameAmountShouldReturnTwoThousandPointsWhenTheAmountIsTwoThousand() {
        assertEquals(donationSameAmount1.calculateSameAmount(), Integer.valueOf(2000));
    }

    @Test
    public void sameAmountShouldReturnZeroPointsWhenTheAmountIsLessThanOneThousand() {
        assertEquals(donationSameAmount2.calculateSameAmount(), Integer.valueOf(0));
    }

    @Test
    public void secondColaborationShouldReturnFiveHundredPointsWhenTheDonationsAreEqualOrMoreThanTwo(){
        userForDonations.addDonation(donationSameAmount1);
        assertEquals(donationSecondColab1.calculateSecondColaboration(), Integer.valueOf(500));
    }

    @Test
    public void secondColaborationShouldReturnZeroPointsWhenTheDonationsAreLesserThanTwo(){
        assertEquals(donationSecondColab2.calculateSecondColaboration(), Integer.valueOf(0));
    }
}