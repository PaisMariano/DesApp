package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.*;
import unq.edu.tpi.desapp.model.builders.DonationBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

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

    @PostConstruct
    public void initialize() throws Exception {
        if (className.equals("org.h2.Driver")) {
            //logger.warn("Init Data Using H2 DB");
                //DATOS MAESTROS NECESARIOS.
                fireInitialDataUser();
                fireInitialDataProjectState();
                fireInitialDataProject();

                //DATOS HISTORICOS.
                fireInitialDataDonation();
        }
    }

    private void fireInitialDataUser() throws Exception {
        userService.createUser(new User(
                "Mariano",
                "paismariano@gmail.com",
                "asdf",
                "kvc4",
                new ArrayList<>()));
        userService.createUser(new User(
                "Federico",
                "fedecame@gmail.com",
                "asdf",
                "coloApagaVentilador",
                new ArrayList<>()));
        userService.createUser(new User(
                "Roberto",
                "robertoasd@gmail.com",
                "fasdf",
                "rober123",
                new ArrayList<>()));
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

    private void fireInitialDataProject() throws Exception {
        projectService.createProject(new Project(
                "Proyecto Bernal",
                10000,
                75.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 22),
                new Location("Bernal","Buenos Aires",57589),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Castelar",
                1000,
                60.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 10),
                new Location("Castelar","Buenos Aires",65841),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Tigre",
                5000,
                65.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 22),
                new Location("Tigre","Buenos Aires",75241),
                projectStateService.findByID(1)));

    }

    private void fireInitialDataDonation() throws Exception {
        Donation donacion1Usuario1 = DonationBuilder.aDonation()
                .withAmount(10000)
                .withComment("Donacion!!!")
                .withUser(userService.findByID(1))
                .withProject(projectService.findByID(4))
                .build();
        Donation donacion2Usuario1 = DonationBuilder.aDonation()
                .withAmount(1000)
                .withComment("Donacion normal!")
                .withUser(userService.findByID(1))
                .withProject(projectService.findByID(4))
                .build();
        Donation donacion3Usuario1 = DonationBuilder.aDonation()
                .withAmount(50)
                .withComment("Donacioncita!!!")
                .withUser(userService.findByID(1))
                .withProject(projectService.findByID(4))
                .build();

        Donation donacion1Usuario2 = DonationBuilder.aDonation()
                .withAmount(500)
                .withComment("Done lo que pude!")
                .withUser(userService.findByID(2))
                .withProject(projectService.findByID(4))
                .build();
        Donation donacion2Usuario2 = DonationBuilder.aDonation()
                .withAmount(5000)
                .withComment("Donacion Copada!!!")
                .withUser(userService.findByID(2))
                .withProject(projectService.findByID(6))
                .build();

        Donation donacion1Usuario3 = DonationBuilder.aDonation()
                .withAmount(100000)
                .withComment("Super Donacion!!!")
                .withUser(userService.findByID(3))
                .withProject(projectService.findByID(8))
                .build();

        donationService.save(donacion1Usuario1);
        donationService.save(donacion2Usuario1);
        donationService.save(donacion3Usuario1);
        donationService.save(donacion1Usuario2);
        donationService.save(donacion2Usuario2);
        donationService.save(donacion1Usuario3);
    }

}
