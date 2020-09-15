package unq.edu.tpi.desapp.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import unq.edu.tpi.desapp.model.builders.ProjectBuilder;

import static org.junit.Assert.*;

public class ProjectTest {

    @Test
    public void projectsAreCreatedWith0FundsInitially() {
        Project newProject = ProjectBuilder.aProject().build();
        assertEquals((Integer)0, newProject.getRaisedFunds());
    }

}