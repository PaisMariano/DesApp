package unq.edu.tpi.desapp.webservices;

import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.services.UserService;
import unq.edu.tpi.desapp.exceptions.BadRequestException;
import unq.edu.tpi.desapp.exceptions.ElementAlreadyExists;
import unq.edu.tpi.desapp.exceptions.UserNotFoundException;

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

//    @PostMapping("/login")
//    public User createUser(@RequestBody String email) throws BadRequestException {
//        User user;
//        // TODO(fede): Ver como obtener los datos del usuario a traves del token para que sea mas seguro..porque deberia usar el password (aunque no tenga sentido ahora en el back, para ahorrar un refactor.)
//        try {
//            user = userService.createUserImproved(email, "pepe", "asdf1234*", "pepitaa");
//            ResponseEntity.status(HttpStatus.CREATED);
//        } catch(ElementAlreadyExists ex) {
//            user = userService.findByEmail(email);
//            ResponseEntity.status(HttpStatus.OK);
//        }
//        return user;
//    }

//    @GetMapping("/users/{id}")
//    public User getUser(@PathVariable("id") Integer id) throws UserNotFoundException {
//        return userService.findByID(id);
//    }

    @GetMapping("/users/{email}")
    public User getUser(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }
}
