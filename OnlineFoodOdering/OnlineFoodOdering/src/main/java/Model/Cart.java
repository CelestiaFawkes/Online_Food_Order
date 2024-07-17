package Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@OneToOne
	private User customer;

	private Long total;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItems> item = new ArrayList<>();

}
