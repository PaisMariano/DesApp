package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User model) {
        return this.userRepository.save(model);
    }

    public User findByID(Integer id) {
        return this.userRepository.findById(id).get();
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public void createUser(String name, String email, String password, String nickname) {
        //probablemente aca se haga la encriptacion de la password.
        User user = new User(name, email, password, nickname, new ArrayList<>());
        save(user);
    }


}
