package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.services.UserService;

import java.util.List;


@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.findAll();
    }
}
