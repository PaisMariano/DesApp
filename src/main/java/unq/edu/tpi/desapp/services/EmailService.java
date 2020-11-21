package unq.edu.tpi.desapp.services;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import unq.edu.tpi.desapp.exceptions.FailedEmailException;
import unq.edu.tpi.desapp.exceptions.ProjectNotFoundException;
import unq.edu.tpi.desapp.helpers.EmailServiceConfig;
import unq.edu.tpi.desapp.model.Project;

@Service
public class EmailService {

    private static final String TEMPLATE_CLASSPATH_RES = "classpath:mail/editablehtml/email-editable.html";
    private static final String TEMPLATE_CLASSPATH_PROJECT = "classpath:mail/editablehtml/projectEnd.html";

    private static final String BACKGROUND_IMAGE = "mail/editablehtml/images/background.png";
    private static final String LOGO_BACKGROUND_IMAGE = "mail/editablehtml/images/logo-background.png";
    private static final String THYMELEAF_BANNER_IMAGE = "mail/editablehtml/images/thymeleaf-banner.png";
    private static final String THYMELEAF_LOGO_IMAGE = "mail/editablehtml/images/thymeleaf-logo.png";

    private static final String PNG_MIME = "image/png";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    private ProjectService projectService;

//    @Autowired
//    private UserService userService;

    public void sendDonorsEmail(final Locale locale) throws FailedEmailException {
        List<String> donations = projectService.dailyTopTenDonations();
//        List<User> users = userService.findAll();

        try {
            sendEditableMail(
                    "mariano",
                    "mariano_a_p@hotmail.com",
                    "Mira el TOP 10 de donaciones de hoy!",
                    donations,
                    TEMPLATE_CLASSPATH_RES,
                    locale
            );
        } catch (MessagingException | IOException e){
            throw new FailedEmailException("Failed sending email.");
        }
//        users.stream()
//                .forEach(elem -> {
//                    try {
//                        sendEditableMail(
//                                elem.getUsername(),
//                                elem.getEmail(),
//                                "Mira el TOP 10 de donaciones de hoy!",
//                                donations,
//                                locale
//                                );
//                    } catch (MessagingException | IOException e){
//                        e.printStackTrace();
//                    }
//                });
    }

    public void sendLocationsEmail(final Locale locale) throws FailedEmailException {
        List<String> locations = projectService.dailyLeastTenDonatedLocations();
//        List<User> users = userService.findAll();

        try {
            sendEditableMail(
                    "mariano",
                    "mariano_a_p@hotmail.com",
                    "Mira el TOP 10 de locaciones con menos donaciones de hoy y empeza a donar!",
                    locations,
                    TEMPLATE_CLASSPATH_RES,
                    locale
            );
        } catch (MessagingException | IOException e) {
            throw new FailedEmailException("Failed sending email.");
        }
        //        users.stream()
//                .forEach(elem -> {
//                    try {
//                        sendEditableMail(
//                                elem.getUsername(),
//                                elem.getEmail(),
//                                "Mira el TOP 10 de locaciones con menos donaciones de hoy y empeza a donar!",
//                                locations,
//                                locale
//                                );
//                    } catch (MessagingException | IOException e){
//                        e.printStackTrace();
//                    }
//                });
    }

    public void sendEndingProjectEmail(Integer projectId, final Locale locale) throws FailedEmailException, ProjectNotFoundException {
        Project project = projectService.findByID(projectId);

        try {
            sendEditableMail(
                    "mariano",
                    "mariano_a_p@hotmail.com",
                    "Un proyecto en el que donaste se cerro!",
                    Collections.singletonList(project.getName()),
                    TEMPLATE_CLASSPATH_PROJECT,
                    locale
            );
        } catch (MessagingException | IOException e){
            throw new FailedEmailException("Failed sending email.");
        }
    }

    public String getEditableMailTemplate(String classpath) throws IOException {
        final Resource templateResource = this.applicationContext.getResource(classpath);
        final InputStream inputStream = templateResource.getInputStream();
        return IOUtils.toString(inputStream, EmailServiceConfig.EMAIL_TEMPLATE_ENCODING);
    }

    /*
     * Send HTML mail with inline image
     */
    public void sendEditableMail(
            final String recipientName,
            final String recipientEmail,
            final String subject,
            final List<String> displayList,
            final String classpath,
            final Locale locale)
            throws MessagingException, IOException {

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject(subject);
        message.setFrom("DESAPPUNQ@alu.unq.edu.ar");
        message.setTo(recipientEmail);

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("list", displayList);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(getEditableMailTemplate(classpath), ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Add the inline images, referenced from the HTML code as "cid:image-name"
        message.addInline("background", new ClassPathResource(BACKGROUND_IMAGE), PNG_MIME);
        message.addInline("logo-background", new ClassPathResource(LOGO_BACKGROUND_IMAGE), PNG_MIME);
        message.addInline("thymeleaf-banner", new ClassPathResource(THYMELEAF_BANNER_IMAGE), PNG_MIME);
        message.addInline("thymeleaf-logo", new ClassPathResource(THYMELEAF_LOGO_IMAGE), PNG_MIME);

        // Send mail
        this.mailSender.send(mimeMessage);
    }

    @Scheduled(cron = "0 11 19 ? * *")
    public void cronJobSch() throws FailedEmailException {
        sendLocationsEmail(new Locale("Spanish"));
        sendDonorsEmail(new Locale("Spanish"));
    }
}