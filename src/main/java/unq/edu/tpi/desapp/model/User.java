package unq.edu.tpi.desapp.model;

public class User {
    private String username;
    private String email;
    private String password;
    private String nickname;
    private Integer points;

    public User(String username, String email, String password, String nickname){
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.points = 0;
    }

    public String getUsername() {
        return username;
    }

    public void addPoints(Integer givenPoints){
        points = givenPoints;
    }


}
