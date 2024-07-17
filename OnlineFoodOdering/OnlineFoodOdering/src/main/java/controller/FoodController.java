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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Model.Food;
import Model.User;
import Service.FoodService;
import Service.ResturantService;
import Service.userService;
import request.CreateFoodRequest;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

	@Autowired
	private FoodService foodService;

	@Autowired
	private userService UserService;

	@Autowired
	private ResturantService resturantService;

	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(@RequestParam String name, @RequestHeader("Authorization") String jwt)
			throws Exception {
		logger.info("Searching for food by name '{}' with JWT: {}", name, jwt);

		User user = UserService.findUserByJwtToken(jwt);
		List<Food> foods = foodService.searchFood(name);

		return new ResponseEntity<>(foods, HttpStatus.OK);
	}

	@GetMapping("/resturant/{resturantid}")
	public ResponseEntity<List<Food>> getResturantFood(@RequestParam boolean Vegetarain, @RequestParam boolean Seasonal,
			@RequestParam boolean Nonveg, @RequestParam(required = false) String food_category,
			@PathVariable Long resturantId, @RequestHeader("Authorization") String jwt) throws Exception {
		logger.info(
				"Fetching food for restaurant ID '{}' with filters: Vegetarian={}, Seasonal={}, Non-vegetarian={}, Category={}, JWT: {}",
				resturantId, Vegetarain, Seasonal, Nonveg, food_category, jwt);

		User user = UserService.findUserByJwtToken(jwt);
		List<Food> foods = foodService.getResturantsFood(resturantId, Vegetarain, Nonveg, Seasonal, food_category);

		return new ResponseEntity<>(foods, HttpStatus.OK);
	}
}
