package request;

import java.util.List;

import Model.Category;
import Model.IngredientsItems;
import lombok.Data;

@Data
public class CreateFoodRequest {

	private String name;
	private String description;
	private Long price;

	private Category category;
	private List<String> images;

	private Long resturantId;
	private boolean vegetarian;
	private boolean seasonal;
	private List<IngredientsItems> ingredients;

}
