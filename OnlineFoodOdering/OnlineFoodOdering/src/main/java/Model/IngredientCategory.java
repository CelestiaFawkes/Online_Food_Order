package Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ingredient_category")
public class IngredientCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private String name;

	@JsonIgnore
	@ManyToOne
	private Resturant resturant;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<IngredientsItems> ingredients = new ArrayList<>();

}
