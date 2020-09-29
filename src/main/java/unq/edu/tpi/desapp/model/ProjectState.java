package unq.edu.tpi.desapp.model;

import unq.edu.tpi.desapp.model.exceptions.EndDateMustBeAfterStartDate;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;
import unq.edu.tpi.desapp.model.exceptions.InvalidFactor;
import unq.edu.tpi.desapp.model.exceptions.InvalidMinClosePercentage;

public abstract class ProjectState {
    private String state;

    public String getState() {
        return state;
    }

    public ProjectState(String state) {
        this.state = state;
    }

    public void complete(Project aProject) {
        // reusable no behaviour method
    }
    public void donate(Integer amount, String comment, User user, Project project) throws IntegerMustBePositive, EndDateMustBeAfterStartDate, InvalidMinClosePercentage, InvalidFactor {
        // reusable no behaviour method
    }
}
