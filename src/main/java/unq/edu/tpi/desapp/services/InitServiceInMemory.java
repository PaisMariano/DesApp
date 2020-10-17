package unq.edu.tpi.desapp.services;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.*;
import unq.edu.tpi.desapp.model.builders.DonationBuilder;
import unq.edu.tpi.desapp.model.builders.ProjectBuilder;
import unq.edu.tpi.desapp.model.builders.UserBuilder;
import unq.edu.tpi.desapp.model.exceptions.*;

@Service
@Transactional
public class InitServiceInMemory {

    //protected final Logger logger = LogManager.getLogger(getClass());

    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectStateService projectStateService;

    @Autowired
    private UserService userService;

    @Autowired
    private DonationService donationService;

    @Autowired
    private LocationService locationService;

    @PostConstruct
    public void initialize() {
        if (className.equals("org.h2.Driver")) {
            //logger.warn("Init Data Using H2 DB");
            try {
                //DATOS MAESTROS NECESARIOS.
                fireInitialDataUser();
                fireInitialDataProjectState();
                fireInitialDataProject();

                //DATOS HISTORICOS.
                fireInitialDataDonation();

            } catch (EndDateMustBeAfterStartDate |
                    BadEmailAddressException |
                    IntegerMustBePositive |
                    InvalidMinClosePercentage |
                    InvalidFactor ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void fireInitialDataProjectState() {
        ProjectState planned = new Planned();
        planned.setId(1);
        projectStateService.save(planned);

        ProjectState connected = new Connected();
        connected.setId(2);
        projectStateService.save(connected);

        ProjectState suspended = new Suspended();
        suspended.setId(3);
        projectStateService.save(suspended);
    }

    private void fireInitialDataProject() throws EndDateMustBeAfterStartDate, InvalidFactor, IntegerMustBePositive, InvalidMinClosePercentage {
        Project project1 = ProjectBuilder.aProject()
                .withProjectState(projectStateService.findByID(1))
                .withLocation(new Location("Pepe","hola",2000))
                .build();
        project1.addParticipant(userService.findAll().get(0));
        Project project2 = ProjectBuilder.aProject()
                .withProjectState(projectStateService.findByID(1))
                .withLocation(new Location("Pepe2","hola",2000))
                .build();

        projectService.save(project1);
        projectService.save(project2);

    }

    private void fireInitialDataUser() throws BadEmailAddressException {
        User user = UserBuilder.aUser().build();
        userService.save(user);
    }

    private void fireInitialDataDonation() throws EndDateMustBeAfterStartDate, InvalidFactor, IntegerMustBePositive, InvalidMinClosePercentage, BadEmailAddressException {
        Donation donation = DonationBuilder.aDonation()
                .withProject(projectService.findAllProjects().get(0))
                .withUser(userService.findAll().get(0))
                .build();
        donationService.save(donation);
    }
}
