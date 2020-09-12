package unq.edu.tpi.desapp.model.builders;

import unq.edu.tpi.desapp.model.User;

public class UserBuilder {
    public static UserBuilder aUser() {
        return new UserBuilder();
    }
    private String username = "noUsername";
    private String email = "noEmail";
    private String password = "noPassword";
    private String nickname = "noNickname";

    public User build() {
        return new User(username, email, password, nickname);
    }

    //Faltan los withX - EJ withName(final String name) { }
}
