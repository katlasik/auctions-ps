package pl.sda.auctions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.sda.auctions.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
