package unq.edu.tpi.desapp.services;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.*;
import unq.edu.tpi.desapp.model.builders.DonationBuilder;
import unq.edu.tpi.desapp.model.builders.ProjectBuilder;
import unq.edu.tpi.desapp.model.builders.UserBuilder;
import unq.edu.tpi.desapp.model.exceptions.EndDateMustBeAfterStartDate;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;
import unq.edu.tpi.desapp.model.exceptions.InvalidFactor;
import unq.edu.tpi.desapp.model.exceptions.InvalidMinClosePercentage;

@Service
@Transactional
public class InitServiceInMemory {

    protected final Log logger = LogFactory.getLog(getClass());

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
            logger.warn("Init Data Using H2 DB");
            try {
                //DATOS MAESTROS NECESARIOS.
                fireInitialDataUser();
                fireInitialDataProjectState();
                fireInitialDataProject();

                //DATOS HISTORICOS.
                fireInitialDataDonation();

            } catch (EndDateMustBeAfterStartDate endDateMustBeAfterStartDate) {
                endDateMustBeAfterStartDate.printStackTrace();
            } catch (InvalidFactor invalidFactor) {
                invalidFactor.printStackTrace();
            } catch (IntegerMustBePositive integerMustBePositive) {
                integerMustBePositive.printStackTrace();
            } catch (InvalidMinClosePercentage invalidMinClosePercentage) {
                invalidMinClosePercentage.printStackTrace();
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
                .build();
        project1.addParticipant(userService.findAll().get(0));
        Project project2 = ProjectBuilder.aProject()
                .build();

        projectService.save(project1);
        projectService.save(project2);
    }

    private void fireInitialDataUser() {
        User user = UserBuilder.aUser().build();
        userService.save(user);
    }

    private void fireInitialDataDonation() throws EndDateMustBeAfterStartDate, InvalidFactor, IntegerMustBePositive, InvalidMinClosePercentage {
        Donation donation = DonationBuilder.aDonation()
                .withProject(projectService.findAll().get(0))
                .withUser(userService.findAll().get(0))
                .build();
        donationService.save(donation);
    }
}
