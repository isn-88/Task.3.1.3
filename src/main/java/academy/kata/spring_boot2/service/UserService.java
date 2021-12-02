package academy.kata.spring_boot2.service;

import academy.kata.spring_boot2.model.Role;
import academy.kata.spring_boot2.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;


public interface UserService extends UserDetailsService {


    User findByEmail(String username);

    Set<Role> getAllRoles();

    User findById(Long id);

    List<User> getAllUsers();

    void saveUser(User user);

    void deleteById(Long id);

    Role getRoleUser();


}
