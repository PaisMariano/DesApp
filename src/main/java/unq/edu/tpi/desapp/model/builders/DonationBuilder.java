package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.model.User;

import java.time.LocalDate;

public class DonationBuilder {
    public static DonationBuilder aDonation(){
        return new DonationBuilder();
    }
    private Integer amount = 0;
    private String comment = "no_comment";
    private LocalDate date = LocalDate.now();
    private User user = UserBuilder.aUser().build();
    private Project project = ProjectBuilder.aProject().build();
}
