package pl.sda.auctions.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.auctions.model.Role;
import pl.sda.auctions.model.dto.ChangePasswordForm;
import pl.sda.auctions.model.dto.UserRegistrationForm;
import pl.sda.auctions.services.SecurityService;
import pl.sda.auctions.services.UserService;

import javax.validation.Valid;

@Controller
public class ChangePasswordController {
    private final UserService userService;
    private final SecurityService securityService;

    public ChangePasswordController(UserService userService, SecurityService securityService) {

        this.userService = userService;
        this.securityService = securityService;
    }


    @GetMapping("/change_password")
    public String getChangePassword(@ModelAttribute("change_password") ChangePasswordForm changePasswordForm) {
        if (securityService.userIsLoggedIn()) {
            return "change_password";
        } else {
            return "redirect:";
        }
    }


//    @PutMapping("/change_password")
//    public ChangePasswordForm updatePassword(Model model) {
//        var email = SecurityContextHolder.getContext().getAuthentication().getName();
//        String password = userService.changePassword().map(u -> u.getName()).orElse(" ");
//        String user = userService.getUserByEmail(email).map(u -> u.getName()).orElse(" ");
//        model.addAttribute("email", email);
//        model.addAttribute("password", password);
//        return "change_password";
//    }

    @PostMapping("/change_password")
    public String updatePassword(@ModelAttribute("change_password") @Valid ChangePasswordForm form,
                                   BindingResult bindingResult, RedirectAttributes attributes) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!form.getNewPassword().equals(form.getRetypedNewPassword())) {
            bindingResult.rejectValue("password", "change.password.errorMsg.passwordMismatch");
        } else if (!bindingResult.hasErrors()) {
            userService.changePassword(email, form.getOldPassword(), form.getRetypedNewPassword());
            attributes.addFlashAttribute("success", "change.password.success");
            return "redirect:/profile";
        }
        return "change_password";
    }

}