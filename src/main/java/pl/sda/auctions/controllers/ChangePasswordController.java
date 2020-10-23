package pl.sda.auctions.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sda.auctions.dto.UserDto;
import pl.sda.auctions.model.User;
import pl.sda.auctions.services.ChangePasswordService;
import pl.sda.auctions.services.UserService;

@Controller
public class ChangePasswordController {
    private final UserService userService;
    private final ChangePasswordService changePasswordService;

    public ChangePasswordController(UserService userService, ChangePasswordService changePasswordService) {

        this.userService = userService;
        this.changePasswordService = changePasswordService;
    }

    @GetMapping("/changePassword")
    public String getChangePasswordPage() {
        return "changePassword";
    }

    @PutMapping("/changePassword")
    public UserDto updatePassword(Model model) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        String password = changePasswordService.changePassword().map(u -> u.getName()).orElse(" ");
        String user = userService.getUserByEmail(email).map(u -> u.getName()).orElse(" ");
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        return "change password";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        String user = userService.getUserByEmail(email).map(u -> u.getName()).orElse(" ");
        model.addAttribute("name", user);
        model.addAttribute("email", email);
        return "profile";
    }
}