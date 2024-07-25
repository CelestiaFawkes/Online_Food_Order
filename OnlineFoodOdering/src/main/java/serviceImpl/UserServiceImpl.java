package serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.User;
import Repository.UserRepository;
import Service.userService;
import config.JwtProvider;

@Service
public class UserServiceImpl implements userService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		logger.debug("Finding user by JWT token");

		// Decode JWT to extract email
		String email = jwtProvider.getEmailFromJwtToken(jwt);

		// Find user by email
		User user = findUserByEmail(email);

		logger.info("User found by JWT token: {}", user.getEmail());
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		logger.debug("Finding user by email '{}'", email);

		User user = userRepository.findByEmail(email);
		if (user == null) {
			logger.error("User not found with email '{}'", email);
			throw new Exception("User not found");
		}

		logger.info("User found successfully with email '{}'", email);
		return user;
	}

}
