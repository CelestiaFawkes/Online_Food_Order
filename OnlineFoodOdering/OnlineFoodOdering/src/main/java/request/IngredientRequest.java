package request;

import lombok.Data;

@Data
public class IngredientRequest {
	
	private String name;
	private Long resturantId;
	private Long categoryId;

}
