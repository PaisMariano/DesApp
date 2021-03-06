package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.aspects.ExceptionAspect;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.repositories.UserRepository;
import unq.edu.tpi.desapp.exceptions.ElementAlreadyExists;
import unq.edu.tpi.desapp.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User model) {
        return this.userRepository.save(model);
    }

    public User findByID(Integer id) throws Exception {
        return this.userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.createWith(id.toString()));
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

    @ExceptionAspect
    public User createUser(User user) throws Exception {
        User newUser = null;

        newUser = new User(user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                new ArrayList<>());
        if (findByEmail(newUser.getEmail()) != null){
            throw new ElementAlreadyExists("Email already exists.");
        }

        save(newUser);
        return newUser;
    }
}
