package Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Model.User;
import Model.userrole;
import Repository.UserRepository;

@Service
public class CustomerUserDetailService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerUserDetailService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("Attempting to load user details for username: {}", username);

		User user = userRepository.findByEmail(username);
		if (user == null) {
			logger.error("No user found with the email: {}", username);
			throw new UsernameNotFoundException("No user found with the email " + username);
		}

		userrole role = user.getRole();
		if (role == null) {
			logger.warn("User {} does not have a specified role. Assigning default role: ROLE_CUSTOMER", username);
			role = userrole.ROLE_CUSTOMER;
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));

		logger.info("User {} loaded successfully with role: {}", username, role);
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
