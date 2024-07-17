package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "food")
@SequenceGenerator(name = "food_seq", sequenceName = "food_seq", allocationSize = 1)
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private String name;

	private String description;

	private Long price;

	@ManyToOne
	private Category foodcategory;

	@ElementCollection
	@CollectionTable(name = "food_images", joinColumns = @JoinColumn(name = "food_id"))
	@Column(name = "image_url", length = 1000)

	private List<String> images;

	private boolean available;

	@ManyToOne
	private Resturant resturant;

	private boolean isVegetarian;

	private boolean isSeasonal;

	@ManyToMany

	@JoinTable(name = "food_ingredients", joinColumns = @JoinColumn(name = "food_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	private List<IngredientsItems> ingrediensts = new ArrayList<>();

	private Date CreationDate;

}
