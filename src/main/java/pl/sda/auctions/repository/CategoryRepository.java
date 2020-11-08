package pl.sda.auctions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import pl.sda.auctions.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByName(String name);

	@Query("select count(c) > 0 from Category c where c.name = ?1")
	boolean checkIfCategoryExists(String name);

}
