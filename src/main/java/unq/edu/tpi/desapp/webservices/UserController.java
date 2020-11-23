package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.services.UserService;
import unq.edu.tpi.desapp.exceptions.BadRequestException;

import javax.validation.Valid;
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
    public User createUser(@Valid @RequestBody User user, BindingResult errors) throws Exception {
        if (errors.hasErrors())
            throw BadRequestException.createWith("JSON bad request or missing field.");

        User newUser = userService.createUser(user);

        ResponseEntity.status(HttpStatus.CREATED);

        return newUser;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Integer id) throws Exception {
        return userService.findByID(id);
    }
}
