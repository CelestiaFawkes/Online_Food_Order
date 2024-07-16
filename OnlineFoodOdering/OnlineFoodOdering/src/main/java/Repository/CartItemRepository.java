package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Model.CartItems;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long>{

}
