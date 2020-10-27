package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.services.UserService;
import unq.edu.tpi.desapp.webservices.exceptions.BadRequestException;
import unq.edu.tpi.desapp.webservices.exceptions.ElementAlreadyExists;
import unq.edu.tpi.desapp.webservices.exceptions.UserNotFoundException;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> allUsers() {
        return userService.findAll();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) throws BadRequestException, ElementAlreadyExists {
        User newUser = userService.createUser(user);

        ResponseEntity.status(HttpStatus.CREATED);

        return newUser;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Integer id) throws UserNotFoundException {
        return userService.findByID(id);
    }
}
