package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.Donation;
import unq.edu.tpi.desapp.model.User;
import java.util.ArrayList;

public class UserBuilder {
    public static UserBuilder aUser() {
        return new UserBuilder();
    }
    private String username = "noUsername";
    private String email = "noEmail";
    private String password = "noPassword";
    private String nickname = "noNickname";
    private ArrayList<Donation> donations = new ArrayList<>();

    public User build() {
        return new User(username, email, password, nickname, donations);
    }
}
