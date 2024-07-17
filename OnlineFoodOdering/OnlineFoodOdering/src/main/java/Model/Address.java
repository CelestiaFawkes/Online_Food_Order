package Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
@SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

}
