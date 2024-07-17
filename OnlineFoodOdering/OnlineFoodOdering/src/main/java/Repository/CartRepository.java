package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	public Cart findByCustomerId(long userId);

}
