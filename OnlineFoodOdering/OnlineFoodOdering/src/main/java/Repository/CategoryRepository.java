package Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	public List<Category> findByResturantId(Long resturantId);

}
