package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredient_items")
public class IngredientsItems {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private String name;

	@ManyToOne
	private IngredientCategory category;

	@JsonIgnore
	@ManyToOne
	private Resturant resturant;

	private boolean inStock = true;

}
