package Service;

import java.util.ArrayList;
import java.util.List;

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
public class CustomerUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userrepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userrepository.findByEmail(username);
		if(user!=null)
		{
			throw new UsernameNotFoundException("No user found with the email " + username);
		}
		
		userrole role = user.getRole();
		if(role == null)
			
			role = userrole.ROLE_CUSTOMER;
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}

}
