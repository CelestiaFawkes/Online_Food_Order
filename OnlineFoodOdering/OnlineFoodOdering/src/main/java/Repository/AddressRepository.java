package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
