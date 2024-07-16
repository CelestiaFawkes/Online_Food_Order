package request;

import java.util.List;

import Model.Address;
import Model.Contactinformation;
import lombok.Data;

@Data
public class CreateResturantRequest {
	
	private Long id;
	private String name;
	private String description;
	private String cuisine;
	private Address address;
	private Contactinformation contactInformation;
	private String OpeningHours;
	private List<String> images;

}
