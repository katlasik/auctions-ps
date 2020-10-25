package pl.sda.auctions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.sda.auctions.model.Category;
import pl.sda.auctions.repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public void createCategory(String name, String description) {

		var category = new Category(
				null,
				name,
				description
		);
		categoryRepository.save(category);
		logger.info("Category was created. Category = {}", category);
	}

}
