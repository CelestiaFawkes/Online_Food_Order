package response;

import Model.userrole;
import lombok.Data;

@Data
public class AuthResponse {

	private String jwt;

	private String message;

	private userrole role;

}
