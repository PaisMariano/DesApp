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

    @PostMapping("/sendEditableMail")
    @ResponseBody
    public String sendMailWithInline(
            @RequestParam("recipientName") final String recipientName,
            @RequestParam("recipientEmail") final String recipientEmail,
            @RequestParam("body") final String body,
            final Locale locale)
            throws MessagingException, IOException {

        this.emailService.sendEditableMail(
                recipientName, recipientEmail, body, locale);
        return "redirect:sent.html";
    }
}
