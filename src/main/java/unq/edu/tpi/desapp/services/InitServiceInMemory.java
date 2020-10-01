package unq.edu.tpi.desapp.services;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InitServiceInMemory {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @Autowired
    private ProjectService projectService;

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
            fireInitialDataProject();
        }
    }

    private void fireInitialDataLocation() {
        //Car car = new Car(1, "PNA 879", "Renault Clio");
        //carService.save(car);
    }

    private void fireInitialDataProject() {
        //Car car = new Car(1, "PNA 879", "Renault Clio");
        //carService.save(car);
    }

    private void fireInitialDataUser() {
        //Car car = new Car(1, "PNA 879", "Renault Clio");
        //carService.save(car);
    }

    private void fireInitialDataDonation() {
        //Car car = new Car(1, "PNA 879", "Renault Clio");
        //carService.save(car);
    }
}
