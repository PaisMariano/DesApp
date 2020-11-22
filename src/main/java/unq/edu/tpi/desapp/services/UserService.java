package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.model.exceptions.BadEmailAddressException;
import unq.edu.tpi.desapp.repositories.UserRepository;
import unq.edu.tpi.desapp.exceptions.BadRequestException;
import unq.edu.tpi.desapp.exceptions.ElementAlreadyExists;
import unq.edu.tpi.desapp.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    public User findByEmail(String email) {
        List<User> locations = findAll()
                .stream()
                .filter(elem -> elem.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());

        if (locations.size() == 0) {
            return null;
        }
        return locations.get(0);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User createUser(User user) throws BadRequestException, ElementAlreadyExists {
        //probablemente aca se haga la encriptacion de la password.
        User newUser = null;
        try {
            newUser = new User(user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getNickname(),
                    new ArrayList<>());
            if (findByEmail(newUser.getEmail()) != null){
                throw new ElementAlreadyExists("Email already exists.");
            }

        } catch (NullPointerException ex) {
            throw BadRequestException.createWith("JSON bad request or missing field.");
        } catch (BadEmailAddressException ex) {
            throw BadRequestException.createWith(ex.getMessage());
        }

        save(newUser);
        return newUser;
    }

//    public User createUserImproved(String email, String username, String password, String nickname) throws BadRequestException, ElementAlreadyExists {
//        //probablemente aca se haga la encriptacion de la password.
//        User newUser = null;
//        try {
//            newUser = new User(username,
//                    email,
//                    password,
//                    nickname,
//                    new ArrayList<>());
//            if (findByEmail(newUser.getEmail()) != null){
//                throw new ElementAlreadyExists("Email already exists.");
//            }
//
//        } catch (NullPointerException ex) {
//            throw BadRequestException.createWith("JSON bad request or missing field.");
//        } catch (BadEmailAddressException ex) {
//            throw BadRequestException.createWith(ex.getMessage());
//        }
//
//        save(newUser);
//        return newUser;
//    }


}
