package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import unq.edu.tpi.desapp.model.*;
import unq.edu.tpi.desapp.model.builders.DonationBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class InitInMemoryService {

    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectStateService projectStateService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize() throws Exception {
        if (className.equals("org.h2.Driver")) {
                //DATOS MAESTROS NECESARIOS.
                fireInitialDataUser();
                fireInitialDataProjectState();
                fireInitialDataProject();

                //DATOS HISTORICOS.
                fireIniDataDonation();
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
                "coloVentilador",
                new ArrayList<>()));
        userService.createUser(new User(
                "Roberto",
                "robertoasd@gmail.com",
                "fasdf",
                "rober123",
                new ArrayList<>()));
        userService.createUser(new User(
                "Juliana",
                "juliana@gmail.com",
                "asdf",
                "juliana",
                new ArrayList<>()));
        userService.createUser(new User(
                "Horacio",
                "horacio@gmail.com",
                "asdf",
                "horacio",
                new ArrayList<>()));
        userService.createUser(new User(
                "Pablo",
                "pablo@gmail.com",
                "fasdf",
                "pablo",
                new ArrayList<>()));
        userService.createUser(new User(
                "Matias",
                "mati@gmail.com",
                "asdf",
                "mati",
                new ArrayList<>()));
        userService.createUser(new User(
                "Juan Jose",
                "juanjo@gmail.com",
                "asdf",
                "juanjo",
                new ArrayList<>()));
        userService.createUser(new User(
                "Adriana",
                "adriana@gmail.com",
                "fasdf",
                "adri",
                new ArrayList<>()));
        userService.createUser(new User(
                "Pepe",
                "pepe@gmail.com",
                "fasdf",
                "pepe",
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
                100,
                75.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 22),
                new Location("Bernal","Buenos Aires",57589),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Castelar",
                100,
                60.0f,
                LocalDate.now(),
                LocalDate.of(2020, 11, 29),
                new Location("Castelar","Buenos Aires",65841),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Tigre",
                500,
                65.0f,
                LocalDate.now(),
                LocalDate.of(2020, 11, 28),
                new Location("Tigre","Buenos Aires",75241),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Carlos Paz",
                500,
                65.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 22),
                new Location("Carlos Paz","Cordoba",45000),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Lanus",
                500,
                65.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 22),
                new Location("Lanus","Buenos Aires",35000),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Ezpeleta",
                500,
                65.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 22),
                new Location("Ezpeleta","Buenos Aires",45000),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Berazategui",
                5000,
                65.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 22),
                new Location("Berazategui","Buenos Aires",90000),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Moron",
                500,
                65.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 26),
                new Location("Moron","Buenos Aires",75241),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Ituzaingo",
                500,
                65.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 26),
                new Location("Ituzaingo","Buenos Aires",75241),
                projectStateService.findByID(1)));
        projectService.createProject(new Project(
                "Proyecto Tolosa",
                750,
                65.0f,
                LocalDate.now(),
                LocalDate.of(2022, 12, 26),
                new Location("Tolosa","Buenos Aires",75241),
                projectStateService.findByID(1)));

    }

    private void fireIniDataDonation() throws Exception {
        Donation donacion1Usuario1 = DonationBuilder.aDonation()
                .withAmount(10000)
                .withComment("Donacion!!!")
                .withUser(userService.findByID(1))
                .withProject(projectService.findByID(11))
                .build();
        Donation donacion2Usuario1 = DonationBuilder.aDonation()
                .withAmount(1000)
                .withComment("Donacion normal!")
                .withUser(userService.findByID(1))
                .withProject(projectService.findByID(11))
                .build();
        Donation donacion3Usuario1 = DonationBuilder.aDonation()
                .withAmount(50)
                .withComment("Donacioncita!!!")
                .withUser(userService.findByID(1))
                .withProject(projectService.findByID(11))
                .build();
        Donation donacion1Usuario2 = DonationBuilder.aDonation()
                .withAmount(500)
                .withComment("Done lo que pude!")
                .withUser(userService.findByID(2))
                .withProject(projectService.findByID(11))
                .build();
        Donation donacion2Usuario2 = DonationBuilder.aDonation()
                .withAmount(5000)
                .withComment("Donacion Copada!!!")
                .withUser(userService.findByID(2))
                .withProject(projectService.findByID(13))
                .build();
        Donation donacion1Usuario3 = DonationBuilder.aDonation()
                .withAmount(10150)
                .withComment("Super Donacion 2!!!")
                .withUser(userService.findByID(3))
                .withProject(projectService.findByID(15))
                .build();
        Donation donacion1Usuario4 = DonationBuilder.aDonation()
                .withAmount(1100)
                .withComment("Donacion 2!!!")
                .withUser(userService.findByID(4))
                .withProject(projectService.findByID(17))
                .build();
        Donation donacion1Usuario5 = DonationBuilder.aDonation()
                .withAmount(1000)
                .withComment("Donacion normal 2!")
                .withUser(userService.findByID(5))
                .withProject(projectService.findByID(19))
                .build();
        Donation donacion1Usuario6 = DonationBuilder.aDonation()
                .withAmount(50)
                .withComment("Donacioncita 2!!!")
                .withUser(userService.findByID(6))
                .withProject(projectService.findByID(21))
                .build();
        Donation donacion1Usuario7 = DonationBuilder.aDonation()
                .withAmount(500)
                .withComment("Done lo que pude 2 !")
                .withUser(userService.findByID(7))
                .withProject(projectService.findByID(23))
                .build();
        Donation donacion1Usuario8 = DonationBuilder.aDonation()
                .withAmount(5000)
                .withComment("Donacion Copada 2!!!")
                .withUser(userService.findByID(8))
                .withProject(projectService.findByID(23))
                .build();
        Donation donacion1Usuario9 = DonationBuilder.aDonation()
                .withAmount(11200)
                .withComment("Super Donacion 3!!!")
                .withUser(userService.findByID(9))
                .withProject(projectService.findByID(25))
                .build();
        Donation donacion1Usuario10 = DonationBuilder.aDonation()
                .withAmount(10540)
                .withComment("Donacion 3!!!")
                .withUser(userService.findByID(10))
                .withProject(projectService.findByID(27))
                .build();
        Donation donacion2Usuario10 = DonationBuilder.aDonation()
                .withAmount(1000)
                .withComment("Donacion normal 3!")
                .withUser(userService.findByID(10))
                .withProject(projectService.findByID(27))
                .build();
        Donation donacion3Usuario10 = DonationBuilder.aDonation()
                .withAmount(50)
                .withComment("Donacioncita 3!!!")
                .withUser(userService.findByID(10))
                .withProject(projectService.findByID(27))
                .build();
        Donation donacion4Usuario10 = DonationBuilder.aDonation()
                .withAmount(500)
                .withComment("Done lo que pude 4!")
                .withUser(userService.findByID(10))
                .withProject(projectService.findByID(27))
                .build();
        Donation donacion5Usuario10 = DonationBuilder.aDonation()
                .withAmount(5000)
                .withComment("Donacion Copada 6!!!")
                .withUser(userService.findByID(10))
                .withProject(projectService.findByID(27))
                .build();
        Donation donacion6Usuario10 = DonationBuilder.aDonation()
                .withAmount(105210)
                .withComment("Super Donacion 10!!!")
                .withUser(userService.findByID(10))
                .withProject(projectService.findByID(27))
                .build();

        projectService.createDonation(11, 1, donacion1Usuario1);
        projectService.createDonation(11, 1, donacion2Usuario1);
        projectService.createDonation(11, 1, donacion3Usuario1);
        projectService.createDonation(11, 2, donacion1Usuario2);
        projectService.createDonation(13, 2, donacion2Usuario2);
        projectService.createDonation(15, 3, donacion1Usuario3);
        projectService.createDonation(17, 4, donacion1Usuario4);
        projectService.createDonation(19, 5, donacion1Usuario5);
        projectService.createDonation(21, 6, donacion1Usuario6);
        projectService.createDonation(23, 7, donacion1Usuario7);
        projectService.createDonation(25, 8, donacion1Usuario8);
        projectService.createDonation(27, 9, donacion1Usuario9);
        projectService.createDonation(29, 10, donacion1Usuario10);
        projectService.createDonation(29, 10, donacion2Usuario10);
        projectService.createDonation(29, 10, donacion3Usuario10);
        projectService.createDonation(29, 10, donacion4Usuario10);
        projectService.createDonation(29, 10, donacion5Usuario10);
        projectService.createDonation(29, 10, donacion6Usuario10);
    }
}
