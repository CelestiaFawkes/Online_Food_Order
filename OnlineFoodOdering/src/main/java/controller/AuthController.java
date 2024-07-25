package controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.Cart;
import Model.User;
import Model.userrole;
import Repository.CartRepository;
import Repository.UserRepository;
import config.JwtProvider;
import request.LoginRequest;
import response.AuthResponse;
import serviceImpl.CustomerUserDetailService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired

	private PasswordEncoder passwordEncoder;

	@Autowired

	private JwtProvider jwtProvider;

	@Autowired

	private CustomerUserDetailService customerUserdetailService;

	@Autowired

	private CartRepository cartRepository;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createuserHandler(@RequestBody User user) throws Exception {
		User isEmailExist = userRepository.findByEmail(user.getEmail());
		if (isEmailExist != null) {
			throw new Exception("An account already exists with this email ");
		}

		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setName(user.getName());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

		User savedUser = userRepository.save(createdUser);

		Cart cart = new Cart();
		cart.setCustomer(savedUser);
		cartRepository.save(cart);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register success");
		authResponse.setRole(savedUser.getRole());

		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signingin(@RequestBody LoginRequest req) {
		String username = req.getEmail();
		String password = req.getPassword();

		Authentication authentication = authenticate(username, password);
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
		String jwt = jwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register success");
		authResponse.setRole(userrole.valueOf(role));

		return new ResponseEntity<>(authResponse, HttpStatus.OK);

	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customerUserdetailService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid User..");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password..");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
