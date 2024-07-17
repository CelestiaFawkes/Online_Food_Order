package Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
@SequenceGenerator(name = "cart_item_seq", sequenceName = "cart_item_seq", allocationSize = 1)
public class CartItems {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@ManyToOne
	private Cart cart;

	@ManyToOne
	private Food food;

	private int quantity;

	@ElementCollection
	@CollectionTable(name = "cart_item_ingredients", joinColumns = @JoinColumn(name = "cart_item_id"))
	@Column(name = "ingredient")
	private List<String> Ingredients;

	private Long totalprice;

}
