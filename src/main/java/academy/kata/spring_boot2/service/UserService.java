package academy.kata.spring_boot2.service;

import academy.kata.spring_boot2.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;



public interface UserService extends UserDetailsService {


    User findByUsername(String username);

    User findById(Long id);

    List<User> findAll();

    void saveUser(User user);

    void deleteById(Long id);


}
