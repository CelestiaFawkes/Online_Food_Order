package request;

import java.util.List;

import lombok.Data;

@Data
public class AddCartItemRequest {
	
	private Long foodId;
	
	private int quantity;
	
	private List<String> ingredients;
	

}
