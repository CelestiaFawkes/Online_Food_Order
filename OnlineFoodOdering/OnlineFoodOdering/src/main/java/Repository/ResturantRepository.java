package Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Model.Resturant;

@Repository
public interface ResturantRepository extends JpaRepository<Resturant, Long> {

	@Query("SELECT r FROM Resturant r WHERE(lower(r.name) LIKE lower(concat('%',:query,'%')) "
			+ "OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%'))")
	List<Resturant> findBySearchQuery(String Query);

	Resturant findOwnerById(Long userId);

	Resturant findResturantById(Long resturantid);

}
