package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import unq.edu.tpi.desapp.model.Donation;
import unq.edu.tpi.desapp.services.DonationService;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class DonationController {
    @Autowired
    private DonationService donationService;

    @GetMapping("/donations")
    public List<Donation> allDonations() {
        return donationService.findAll();
    }
}
