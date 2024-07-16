package Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long>{
	
	List<Food> findResturantById(Long resturantId);
	
	
	@Query("SELECT f FROM Food WHERE f.name LIKE  %:keyword% OR f.foodcategory.name LIKE %:keyword%")
	List<Food> searchFood(@Param("keyword") String keyword);

}
