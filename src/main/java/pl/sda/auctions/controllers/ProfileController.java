package pl.sda.auctions.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.sda.auctions.model.User;
import pl.sda.auctions.services.UserService;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        String user = userService.getUserByEmail(email).map(User::getName).orElse(" ");
        model.addAttribute("name", user);
        model.addAttribute("email", email);
        return "profile";
    }
}
