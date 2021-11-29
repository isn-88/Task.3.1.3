package academy.kata.spring_boot2.controller;

import academy.kata.spring_boot2.model.Role;
import academy.kata.spring_boot2.model.User;
import academy.kata.spring_boot2.repository.RoleRepository;
import academy.kata.spring_boot2.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private RoleRepository roleRepository;


    @GetMapping()
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "all-users";
    }


    @GetMapping("/new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "new-user";
    }


    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new-user";
        }
        Set<Role> roles = user.getRoles();
        for (Role r : roles) {
            r.setId(roleRepository.findByRole(r.getRole()).getId());
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("user-update/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "update-user";
    }


    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "update-user";
        }
        Set<Role> roles = user.getRoles();
        for (Role r : roles) {
            r.setId(roleRepository.findByRole(r.getRole()).getId());
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("user-delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

}
