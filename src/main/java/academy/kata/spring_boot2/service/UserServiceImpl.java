package academy.kata.spring_boot2.service;


import academy.kata.spring_boot2.model.Role;
import academy.kata.spring_boot2.model.User;
import academy.kata.spring_boot2.repository.RoleRepository;
import academy.kata.spring_boot2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    private void postConstruct() {

        if (userRepository.findByEmail("user@user.com") == null) {
            User user = new User();
            user.setName("User");
            user.setSurname("User");
            user.setAge(11);
            user.setEmail("user@user.com");
            user.setPassword("$2a$12$uiRlDZshsSDsmBGGAYtXReQDUGsVQIgkDCKd7QSdlT/iI5QRXR9Vi");
            if (roleRepository.findByRole("ROLE_USER") == null) {
                Role role_user = new Role("ROLE_USER");
                roleRepository.save(role_user);
            }
            Role roleUser = roleRepository.findByRole("ROLE_USER");
            user.addRole(roleUser);
            userRepository.save(user);
        }

        if (userRepository.findByEmail("admin@admin.com") == null) {
            User admin = new User();
            admin.setName("Admin");
            admin.setSurname("Admin");
            admin.setAge(22);
            admin.setEmail("admin@admin.com");
            admin.setPassword("$2a$12$i.jqnF7TZB7F1.3E7ui64uo7QkKgFGThkJE/7bKHQT9GSzbbWyWNa");
            if (roleRepository.findByRole("ROLE_ADMIN") == null) {
                Role role_admin = new Role("ROLE_ADMIN");
                roleRepository.save(role_admin);
            }
            if (roleRepository.findByRole("ROLE_USER") == null) {
                Role role_user = new Role("ROLE_USER");
                roleRepository.save(role_user);
            }
            Role roleAdmin = roleRepository.findByRole("ROLE_ADMIN");
            admin.addRole(roleAdmin);
            Role roleUser = roleRepository.findByRole("ROLE_USER");
            admin.addRole(roleUser);
            userRepository.save(admin);
        }
    }



    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public Set<Role> getAllRoles() {
        List<Role> listRoles = roleRepository.findAll();
        return listRoles.stream().collect(Collectors.toSet());
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (Role r : user.getRoles()) {
            Role findRole = roleRepository.findByRole(r.getRole());
            if (findRole != null) {
                roles.add(findRole);
            }
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
   }


}
