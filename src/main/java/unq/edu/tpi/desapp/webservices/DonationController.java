package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.model.Donation;
import unq.edu.tpi.desapp.services.DonationService;
import unq.edu.tpi.desapp.webservices.exceptions.BadRequestException;
import unq.edu.tpi.desapp.webservices.exceptions.DonationNotFoundException;
import unq.edu.tpi.desapp.webservices.exceptions.ProjectNotFoundException;
import unq.edu.tpi.desapp.webservices.exceptions.UserNotFoundException;

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

    @GetMapping("/donations/{id}")
    public Donation getDonation(@PathVariable("id") Integer id) throws DonationNotFoundException {
        return donationService.findByID(id);
    }

    @PostMapping("/donations/project/{projectId}/user/{userId}")
    public ResponseEntity<String> createDonation(
            @PathVariable("projectId") Integer projectId,
            @PathVariable("userId") Integer userId,
            @RequestBody Donation donation) throws ProjectNotFoundException, UserNotFoundException, BadRequestException {

        donationService.createDonation(projectId, userId, donation);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }
}
