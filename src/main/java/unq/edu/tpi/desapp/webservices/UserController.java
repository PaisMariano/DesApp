package unq.edu.tpi.desapp.webservices;

import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import unq.edu.tpi.desapp.dtos.UserAuth0;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.services.UserService;
import unq.edu.tpi.desapp.exceptions.BadRequestException;
import unq.edu.tpi.desapp.exceptions.ElementAlreadyExists;
import unq.edu.tpi.desapp.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/update_user")
    public User createOrUpdateUser(@RequestBody User user) throws BadRequestException {
        User newUser;
        try {
            newUser = userService.createUser(user);
            ResponseEntity.status(HttpStatus.CREATED);
        } catch (ElementAlreadyExists ex) {
            newUser = userService.updateUser(user);
            ResponseEntity.status(HttpStatus.OK);
        }

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

//    @GetMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestHeader("authorization") String authHeader) throws UserNotFoundException {
//        return new ResponseEntity<String>(authHeader, HttpStatus.OK);
//    }

//    @GetMapping("/login")
//    public ResponseEntity<UserAuth0> loginUser(@RequestHeader("authorization") String authHeader) {
//        // Create and set the "Authorization" header before sending HTTP request
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authHeader);
//        HttpEntity<String> entity = new HttpEntity<>("headers", headers);
//
//        // Use the "RestTemplate" API provided by Spring to make the HTTP request
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<UserAuth0> user = restTemplate.exchange("https://dev-lyitcq2e.us.auth0.com/userinfo", HttpMethod.GET, entity, UserAuth0.class);
//        return new ResponseEntity<UserAuth0>(user.getBody(), HttpStatus.OK);
//    }

    @GetMapping("/login")
    public Map<String, String> loginUser(@RequestHeader("authorization") String authHeader) {
        // Create and set the "Authorization" header before sending HTTP request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("headers", headers);

        // Use the "RestTemplate" API provided by Spring to make the HTTP request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserAuth0> user = restTemplate.exchange("https://dev-lyitcq2e.us.auth0.com/userinfo", HttpMethod.GET, entity, UserAuth0.class);
        HashMap<String, String> map = new HashMap<>();
        try {
            userService.findByID(1);
            map.put("user_status", "logged");
            return map;
        } catch (UserNotFoundException ex) {
            map.put("user_status", "created");
            return map;
        }
    }

//    @GetMapping("/users/{id}")
//    public User getUser(@PathVariable("id") Integer id) throws UserNotFoundException {
//        return userService.findByID(id);
//    }

    @GetMapping("/users/{email}")
    public User getUser(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }
}
