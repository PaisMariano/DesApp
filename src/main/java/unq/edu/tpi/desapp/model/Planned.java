package unq.edu.tpi.desapp.model;

import unq.edu.tpi.desapp.model.builders.DonationBuilder;
import unq.edu.tpi.desapp.model.exceptions.EndDateMustBeAfterStartDate;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;
import unq.edu.tpi.desapp.model.exceptions.InvalidFactor;
import unq.edu.tpi.desapp.model.exceptions.InvalidMinClosePercentage;

import java.time.LocalDate;

public class Planned extends ProjectState {
    public Planned() {
        super("En Planificacion");
    }

    @Override
    public void complete(Project aProject) {
        // en caso de que la fecha actual sea posterior a la de fin, pasarlo a estado Suspended
        if (aProject.getEndDate().isBefore(LocalDate.now())) {
            aProject.setProjectState(new Suspended());
            return;
        }
        // verificar si esta listo para pasar a Connected
        if (aProject.missingPercentageToComplete() <= 0) {
            aProject.setProjectState(new Connected());
        }
    }

    @Override
    public void donate(Integer amount, String comment, User user, Project project) throws IntegerMustBePositive, EndDateMustBeAfterStartDate, InvalidMinClosePercentage, InvalidFactor {
        if (amount < 0) {
            throw new IntegerMustBePositive();
        }
        Donation newDonation = DonationBuilder.aDonation().withAmount(amount).withComment(comment).withUser(user).withProject(project).withDate(LocalDate.now()).build();
        project.addDonation(newDonation);
        user.addPoints(newDonation.calculatePoints());
        project.addParticipant(user);
        project.addFunds(amount);
    }
}
