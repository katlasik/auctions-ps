package pl.sda.auctions.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import pl.sda.auctions.model.Role;
import pl.sda.auctions.model.UserRegistrationForm;
import pl.sda.auctions.services.UserService;

@Controller
public class RegistrationController {

	private final UserService userService;

	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/registration")
	public String getRegistration(@ModelAttribute("registration") UserRegistrationForm user) {
		return "registration";
	}

	@PostMapping("/registration")
	public String postRegistration(@ModelAttribute("registration") @Valid UserRegistrationForm user,
								   BindingResult bindingResult, RedirectAttributes attributes) {

		if (!user.getPassword().equals(user.getRetypedPassword())) {
			bindingResult.rejectValue("password", "registration.errorMsg.passwordMismatch");
		} else if (userService.checkIfNameIsTaken(user.getName())) {
			bindingResult.rejectValue("name", "registration.errorMsg.nameTaken");
		} else if (userService.checkIfMailIsTaken(user.getEmail())) {
			bindingResult.rejectValue("email", "registration.errorMsg.emailTaken");
		} else if (!bindingResult.hasErrors()) {
			userService.createUser(user.getName(), user.getEmail(), user.getPassword(), Role.USER);
			attributes.addFlashAttribute("success","registration.success");
			return "redirect:/login";
		}

		return "registration";
	}

}
