package pl.sda.auctions.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import pl.sda.auctions.model.dto.AuctionForm;
import pl.sda.auctions.services.AuctionService;
import pl.sda.auctions.services.CategoryService;
import pl.sda.auctions.services.SecurityService;
import pl.sda.auctions.services.UserService;

@Controller
public class AuctionController {

	private SecurityService securityService;
	private AuctionService auctionService;
	private UserService userService;
	private CategoryService categoryService;

	public AuctionController(SecurityService securityService, AuctionService auctionService, UserService userService, CategoryService categoryService) {
		this.securityService = securityService;
		this.auctionService = auctionService;
		this.userService = userService;
		this.categoryService = categoryService;
	}

	@GetMapping("/create_auction")
	public String getAuction(@ModelAttribute("auction") AuctionForm auction, Model model, RedirectAttributes attributes) {
		if (securityService.userIsLoggedIn()) {
			model.addAttribute("categories", categoryService.getCategories());
			return "create_auction";
		} else {
			attributes.addFlashAttribute("auctionLogin", "{auction.loginInfo}");
			return "redirect:";
		}
	}

	@PostMapping("/create_auction")
	public String getPostAuction(@ModelAttribute("auction") @Valid AuctionForm auction,
								 BindingResult bindingResult, RedirectAttributes attributes) {
		if (!bindingResult.hasErrors()) {
			String ownerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			attributes.addFlashAttribute("auctionSuccess", "{auction.success");
			auctionService.createAuction(auction.getTitle(),
					auction.getDescription(),
					auction.getPrice(),
					ownerEmail,
					auction.getCategory());
			return "redirect:";
		} else {
			return "create_auction";
		}
	}
}
