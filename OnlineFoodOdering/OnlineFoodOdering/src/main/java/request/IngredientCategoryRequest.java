package request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import Model.IngredientCategory;
import lombok.Data;

@Data
public class IngredientCategoryRequest {
	
	private String name;
	private Long resturantId;
	

}
