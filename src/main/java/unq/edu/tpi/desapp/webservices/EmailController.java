package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.services.EmailService;

import java.util.Locale;

@RestController
@EnableAutoConfiguration
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email/donors")
    @ResponseBody
    public String sendDonationsMail(
            final Locale locale)
            throws Exception {

        this.emailService.sendDonorsEmail(locale);
        return "Email sent";
    }

    @PostMapping("/email/locations")
    @ResponseBody
    public String sendLocationsMail(
            final Locale locale)
            throws Exception {

        this.emailService.sendLocationsEmail(locale);
        return "Email sent";
    }
}
