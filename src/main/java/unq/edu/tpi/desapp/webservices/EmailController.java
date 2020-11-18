package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.services.EmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Locale;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email/donors")
    @ResponseBody
    public String sendDonationsMail(
            final Locale locale)
            throws MessagingException, IOException {

        this.emailService.sendDonorsEmail(locale);
        return "Email sent";
    }

    @PostMapping("/email/locations")
    @ResponseBody
    public String sendLocationsMail(
            final Locale locale)
            throws MessagingException, IOException {

        this.emailService.sendLocationsEmail(locale);
        return "Email sent";
    }
}
