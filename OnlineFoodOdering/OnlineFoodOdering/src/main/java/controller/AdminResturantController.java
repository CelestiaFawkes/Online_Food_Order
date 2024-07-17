package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.Resturant;
import Model.User;
import Service.ResturantService;
import Service.userService;
import request.CreateResturantRequest;
import response.MessageResponse;

@RestController
@RequestMapping("/api/admin/resturant")
public class AdminResturantController {

	private static final Logger logger = LoggerFactory.getLogger(AdminResturantController.class);

	@Autowired
	private ResturantService resturantService;

	@Autowired
	private userService UserService;

	@PostMapping("/api/admin/create")
	public ResponseEntity<Resturant> createResturant(@RequestBody CreateResturantRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("Creating restaurant '{}' by user '{}'", req.getName(), user.getName());

		Resturant resturant = resturantService.createResturant(req, user);

		logger.info("Restaurant '{}' created successfully", resturant.getRestName());
		return new ResponseEntity<>(resturant, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Resturant> updateResturant(@RequestBody CreateResturantRequest req,
			@RequestHeader("Authorization") String jwt, @PathVariable Long Id) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("Updating restaurant with ID '{}' by user '{}'", Id, user.getName());

		Resturant resturant = resturantService.updateResturant(Id, req);

		logger.info("Restaurant with ID '{}' updated successfully", Id);
		return new ResponseEntity<>(resturant, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteResturant(@RequestHeader("Authorization") String jwt,
			@PathVariable Long Id) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("Deleting restaurant with ID '{}' by user '{}'", Id, user.getName());

		resturantService.deleteResturant(Id);

		MessageResponse res = new MessageResponse();
		res.setMessage("Restaurant deleted successfully..");

		logger.info("Restaurant with ID '{}' deleted successfully", Id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Resturant> updateResturantStatus(@RequestHeader("Authorization") String jwt,
			@PathVariable Long Id) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("Updating status for restaurant with ID '{}' by user '{}'", Id, user.getName());

		Resturant resturant = resturantService.updateResturantStatus(Id);

		logger.info("Status updated successfully for restaurant with ID '{}'", Id);
		return new ResponseEntity<>(resturant, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<Resturant> findResturantBYUserId(@RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("Fetching restaurant for user '{}'", user.getName());

		Resturant resturant = resturantService.getResturantByUserId(user.getId());

		logger.info("Fetched restaurant '{}' for user '{}'", resturant.getRestName(), user.getName());
		return new ResponseEntity<>(resturant, HttpStatus.OK);
	}
}
