package academy.kata.spring_boot2.controller;



import academy.kata.spring_boot2.model.User;
import academy.kata.spring_boot2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping()
    public String showUser(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("authUser", user);
        return "show-user";
    }
}
