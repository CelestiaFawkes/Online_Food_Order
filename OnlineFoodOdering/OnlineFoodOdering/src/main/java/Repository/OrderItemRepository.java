package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Model.Orderitems;


@Repository
public interface OrderItemRepository extends JpaRepository<Orderitems,Long>{

}
