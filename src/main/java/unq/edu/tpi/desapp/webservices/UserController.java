package unq.edu.tpi.desapp.webservices;

import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import unq.edu.tpi.desapp.exceptions.ElementAlreadyExists;
import unq.edu.tpi.desapp.exceptions.UserNotFoundException;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.services.UserService;
import unq.edu.tpi.desapp.exceptions.BadRequestException;

import java.util.HashMap;
import javax.validation.Valid;
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
    public User createUser(@Valid @RequestBody User user, BindingResult errors) throws Exception {
        if (errors.hasErrors())
            throw BadRequestException.createWith("JSON bad request or missing field.");

        User newUser = userService.createUser(user);

        ResponseEntity.status(HttpStatus.CREATED);

        return newUser;
    }

    @PostMapping("/update_user")
    public User createOrUpdateUser(@Valid @RequestBody User user, BindingResult errors) throws Exception {
        if (errors.hasErrors())
            throw BadRequestException.createWith("JSON bad request or missing field.");

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

//    @GetMapping("/login")
//    public Map<String, String> loginUser(@RequestHeader("authorization") String authHeader) {
//        // Create and set the "Authorization" header before sending HTTP request
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", authHeader);
//        HttpEntity<String> entity = new HttpEntity<>("headers", headers);
//
//        // Use the "RestTemplate" API provided by Spring to make the HTTP request
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<UserAuth0> user = restTemplate.exchange("https://dev-lyitcq2e.us.auth0.com/userinfo", HttpMethod.GET, entity, UserAuth0.class);
//        HashMap<String, String> map = new HashMap<>();
//        try {
//            userService.findByID(1);
//            map.put("user_status", "logged");
//            return map;
//        } catch (UserNotFoundException ex) {
//            map.put("user_status", "created");
//            return map;
//        }
//    }

//    @GetMapping("/users/{id}")
//    public User getUser(@PathVariable("id") Integer id) throws Exception {
//        return userService.findByID(id);
//    }

    @GetMapping("/users/{email}")
    public User getUser(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }
}
