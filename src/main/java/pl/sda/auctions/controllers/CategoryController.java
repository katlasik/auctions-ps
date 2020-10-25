package pl.sda.auctions.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import pl.sda.auctions.model.dto.CategoryForm;
import pl.sda.auctions.services.CategoryService;

@Controller
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/create_category")
	public String getAuction(@ModelAttribute("category") CategoryForm category) {
			return "create_category";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/create_category")
	public String getPostAuction(@ModelAttribute("category") @Valid CategoryForm category,
								 BindingResult bindingResult, RedirectAttributes attributes) {
		if (categoryService.checkIfCategoryExist(category.getName())) {
			bindingResult.rejectValue("name", "category.errorMsg.categoryExists");
		} else if (!bindingResult.hasErrors()) {
			attributes.addFlashAttribute("categorySuccess", "{category.success");
			categoryService.createCategory(category.getName(),
					category.getDescription()
			);
			return "redirect:";
		}
		return "create_category";
	}
}
