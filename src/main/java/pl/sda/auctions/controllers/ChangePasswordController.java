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
        return "change_password";

    }


    @PostMapping("/change_password")
    public String updatePassword(Model model, @ModelAttribute("change_password") @Valid ChangePasswordForm form,
                                 BindingResult bindingResult, RedirectAttributes attributes) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!form.getNewPassword().equals(form.getRetypedNewPassword())) {
            bindingResult.rejectValue("newPassword", "change.password.errorMsg.passwordMismatch");
        } else if (!bindingResult.hasErrors()) {
            boolean passwordChanged = userService.changePassword(email, form.getOldPassword(), form.getNewPassword());
            if (passwordChanged) {
                attributes.addFlashAttribute("success", "change.password.success");
                return "redirect:/profile";
            } else {
                bindingResult.rejectValue("oldPassword", "change.password.errorMsg.wrongOldPassword");
                return "change_password";

            }

        }
        return "change_password";
    }
}