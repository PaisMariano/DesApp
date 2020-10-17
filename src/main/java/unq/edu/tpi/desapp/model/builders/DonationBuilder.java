package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.Donation;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.model.exceptions.*;

import java.time.LocalDate;

public class DonationBuilder {
    private Integer amount = 0;
    private String comment = "no_comment";
    private LocalDate date = LocalDate.now();
    private User user = UserBuilder.aUser().build();
    private Project project = ProjectBuilder.aProject().build();

    public DonationBuilder() throws EndDateMustBeAfterStartDate, InvalidMinClosePercentage, InvalidFactor, IntegerMustBePositive, BadEmailAddressException {
    }

    public static DonationBuilder aDonation() throws EndDateMustBeAfterStartDate, InvalidMinClosePercentage, InvalidFactor, IntegerMustBePositive, BadEmailAddressException {
        return new DonationBuilder();
    }

    public Donation build() throws IntegerMustBePositive {
        return new Donation(amount, comment, date, user, project);
    }

    public DonationBuilder withDate(LocalDate givenDate) {
        date = givenDate;
        return this;
    }
    public DonationBuilder withAmount(Integer givenAmount) {
        amount = givenAmount;
        return this;
    }
    public DonationBuilder withUser(User givenUser) {
        user = givenUser;
        return this;
    }

    public DonationBuilder withProject(Project givenProject) {
        project = givenProject;
        return this;
    }

    public DonationBuilder withComment(String givenComment) {
        comment = givenComment;
        return this;
    }
}
