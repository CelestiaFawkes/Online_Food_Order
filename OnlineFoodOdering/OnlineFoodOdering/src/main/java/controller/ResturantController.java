package controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Dto.ResturantDto;
import Model.Resturant;
import Model.User;
import Service.ResturantService;
import Service.userService;
import request.CreateResturantRequest;

@RestController
@RequestMapping("/api/resturant")
public class ResturantController {

	private static final Logger logger = LoggerFactory.getLogger(ResturantController.class);

	@Autowired
	private ResturantService resturantService;

	@Autowired
	private userService UserService;

	@GetMapping("/search")
	public ResponseEntity<List<Resturant>> searchResturant(@RequestBody CreateResturantRequest req,
			@RequestHeader("Authorization") String jwt, @RequestParam String keyword) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("User {} is searching for restaurants with keyword: {}", user.getId(), keyword);
		List<Resturant> resturants = resturantService.searchResturant(keyword);
		return new ResponseEntity<>(resturants, HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<Resturant>> getAllResturant(@RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("User {} is fetching all restaurants", user.getId());
		List<Resturant> resturants = resturantService.getAllResturant();
		return new ResponseEntity<>(resturants, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Resturant> findResturantById(@RequestHeader("Authorization") String jwt,
			@PathVariable Long id) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("User {} is fetching restaurant with ID: {}", user.getId(), id);
		Resturant resturant = resturantService.findResturantById(id);
		return new ResponseEntity<>(resturant, HttpStatus.OK);
	}

	@PutMapping("/{id}/add-favorities")
	public ResponseEntity<ResturantDto> addToFavorities(@RequestHeader("Authorization") String jwt,
			@PathVariable Long id) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("User {} is adding restaurant with ID: {} to favorites", user.getId(), id);
		ResturantDto resturantDto = resturantService.addToFavorites(id, user);
		return new ResponseEntity<>(resturantDto, HttpStatus.OK);
	}
}
