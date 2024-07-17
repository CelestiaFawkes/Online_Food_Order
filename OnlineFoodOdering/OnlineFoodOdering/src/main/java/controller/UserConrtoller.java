package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import Model.User;
import Service.userService;

@RestController
public class UserConrtoller {

	private userService userService;

	public ResponseEntity<User> findUserByjwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByEmail(jwt);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
