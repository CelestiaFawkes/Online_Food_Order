package Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Model.IngredientsItems;

@Repository
public interface IngredientItemRepository extends JpaRepository<IngredientsItems, Long> {

	List<IngredientsItems> findResturantById(long id);

}
