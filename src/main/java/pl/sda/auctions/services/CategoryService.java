package pl.sda.auctions.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import pl.sda.auctions.model.Category;
import pl.sda.auctions.repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public boolean checkIfCategoryExist(String name){
		return categoryRepository.checkIfCategoryExists(name);
	}

	public Optional<Category> findByName(String name){
		return categoryRepository.findByName(name);
	}
	public Optional<Category> findById(Long id){
		return categoryRepository.findById(id);
	}

	public List<Category> getCategories(){
		return categoryRepository.findAll();
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
