package unq.edu.tpi.desapp.model;

import org.junit.Test;
import unq.edu.tpi.desapp.model.builders.LocationBuilder;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;

import static org.junit.Assert.*;

public class LocationTest {

    @Test(expected = IntegerMustBePositive.class)
    public void populationMustBeGreaterOrEqualThan1() throws IntegerMustBePositive {
        LocationBuilder.aLocation().withPopulation(-10).build();
    }

    @Test(expected = IntegerMustBePositive.class)
    public void cantSetLowerThan1PopulationEither() throws IntegerMustBePositive {
        Location sampleLocation = LocationBuilder.aLocation().build();
        sampleLocation.setPopulation(0);
    }

    @Test
    public void gettersAndSettersForCoverage() throws IntegerMustBePositive {
        Location sampleLocation = LocationBuilder.aLocation().build();
        sampleLocation.setName("Shangri La");
        sampleLocation.setProvince("Babilonia");
        sampleLocation.setPopulation(999);
        assertEquals("Shangri La", sampleLocation.getName());
        assertEquals("Babilonia", sampleLocation.getProvince());
        assertEquals((Integer) 999, sampleLocation.getPopulation());
    }
}