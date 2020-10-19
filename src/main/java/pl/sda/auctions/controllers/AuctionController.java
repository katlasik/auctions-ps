package pl.sda.auctions.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import pl.sda.auctions.model.User;
import pl.sda.auctions.model.dto.AuctionForm;
import pl.sda.auctions.model.dto.UserRegistrationForm;
import pl.sda.auctions.services.AuctionService;
import pl.sda.auctions.services.SecurityService;
import pl.sda.auctions.services.UserService;

@Controller
public class AuctionController {

	private SecurityService securityService;
	private AuctionService auctionService;
	private UserService userService;

	public AuctionController(SecurityService securityService, AuctionService auctionService, UserService userService) {
		this.securityService = securityService;
		this.auctionService = auctionService;
		this.userService = userService;
	}

	@GetMapping("/create_auction")
	public String getRegistration(@ModelAttribute("auction") AuctionForm auction, RedirectAttributes attributes) {
		if (securityService.userIsLoggedIn()) {
			return "create_auction";
		} else {
			attributes.addFlashAttribute("auctionLogin", "{auction.loginInfo}");
			return "redirect:";
		}
	}

	@PostMapping("/create_auction")
	public String getPostAuction(@ModelAttribute("auction") @Valid AuctionForm auction,
								 BindingResult bindingResult, RedirectAttributes attributes) throws Exception {
		if (!bindingResult.hasErrors()) {
			var email = SecurityContextHolder.getContext().getAuthentication().getName();
			String ownerEmail = userService.getUserByEmail(email).map(User::getEmail).orElse(" ");
			attributes.addFlashAttribute("auctionSuccess", "{auction.success");
			auctionService.createAuction(auction.getTitle(),
					auction.getDescription(),
					auction.getPrice(),
					ownerEmail
					);
			return "redirect:";
		} else {
			return "create_auction";
		}
	}
}
