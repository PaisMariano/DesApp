package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.model.exceptions.BadEmailAddressException;
import unq.edu.tpi.desapp.repositories.UserRepository;
import unq.edu.tpi.desapp.webservices.exceptions.BadRequestException;
import unq.edu.tpi.desapp.webservices.exceptions.ProjectNotFoundException;
import unq.edu.tpi.desapp.webservices.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User model) {
        return this.userRepository.save(model);
    }

    public User findByID(Integer id) throws UserNotFoundException {
        User newUser = null;
        try {
            newUser = this.userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw UserNotFoundException.createWith(id.toString());
        }
        return newUser;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public void createUser(User user) throws BadRequestException {
        //probablemente aca se haga la encriptacion de la password.
        User newUser = null;
        try {
            newUser = new User(user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getNickname(),
                    new ArrayList<>());
        } catch (NullPointerException ex) {
            throw BadRequestException.createWith("JSON bad request or missing field.");
        } catch (BadEmailAddressException ex) {
            throw BadRequestException.createWith(ex.getMessage());
        }

        save(newUser);

    }


}
