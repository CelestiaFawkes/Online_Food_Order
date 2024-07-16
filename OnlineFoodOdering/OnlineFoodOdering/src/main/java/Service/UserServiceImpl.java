package Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.User;
import Repository.UserRepository;
import config.JwtProvider;

@Service
public class UserServiceImpl implements userService{
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private userService UserService;
	
	

	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		User users = UserService.findUserByJwtToken(jwt);
		String email = jwtProvider.getEmailFromJwtToken(users.getEmail());
		User user = findUserByEmail(email);
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user = userRepository.findByEmail(email);
		if(user == null)
		{
			throw new Exception("User not found..");
		}
		return user;
	}

}
