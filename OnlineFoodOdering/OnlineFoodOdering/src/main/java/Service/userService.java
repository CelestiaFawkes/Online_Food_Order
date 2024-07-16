package Service;

import Model.User;

public interface userService {
	
	public User findUserByJwtToken(String jwt) throws Exception;
	
	public User findUserByEmail(String email) throws Exception;


}
