package academy.kata.spring_boot2.controller;


import academy.kata.spring_boot2.model.User;
import academy.kata.spring_boot2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;


    @GetMapping()
    public String allUsers(Model model, Principal principal) {
        User authUser = userService.findByEmail(principal.getName());
        model.addAttribute("authUser", authUser);
        model.addAttribute("editUser", new User());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "all-users";
    }


    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
