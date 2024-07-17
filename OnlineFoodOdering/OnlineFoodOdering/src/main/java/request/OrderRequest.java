package request;

import Model.Address;
import lombok.Data;

@Data
public class OrderRequest {

	private Long resturantId;
	private Address deliveryAddress;

}
