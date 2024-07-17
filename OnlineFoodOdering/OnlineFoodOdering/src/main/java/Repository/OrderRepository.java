package Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	public List<Order> findByCustomerId(Long userId);

	public List<Order> findByResturantId(Long resturantId);

}
