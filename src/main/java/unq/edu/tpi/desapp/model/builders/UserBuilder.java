package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.Donation;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.model.exceptions.BadEmailAddressException;

import java.util.ArrayList;

public class UserBuilder {
    private String username = "noUsername";
    private String email = "paismariano@gmail.com";
    private String password = "noPassword";
    private String nickname = "noNickname";
    private ArrayList<Donation> donations = new ArrayList<>();

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public User build() throws BadEmailAddressException {
        return new User(username, email, password, nickname, donations);
    }
}
