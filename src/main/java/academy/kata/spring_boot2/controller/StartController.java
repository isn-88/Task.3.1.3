package academy.kata.spring_boot2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping()
public class StartController {
    @GetMapping()
    public String goLogin() {
        return "redirect:/login";
    }
}
