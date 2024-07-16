package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.Food;
import Model.Resturant;
import Model.User;
import Service.FoodService;
import Service.ResturantService;
import Service.userService;
import request.CreateFoodRequest;
import response.MessageResponse;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

	@Autowired
	private FoodService foodService;

	@Autowired
	private userService UserService;

	@Autowired
	private ResturantService resturantService;

	@PostMapping
	public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req, 
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = UserService.findUserByJwtToken(jwt);
		Resturant resturant = resturantService.findResturantById(req.getResturantId());
		Food food = foodService.CreateFood(req, req.getCategory(), resturant);

		return new ResponseEntity<>(food,HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id, 
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = UserService.findUserByJwtToken(jwt);
		foodService.deleteFood(id);
		MessageResponse res = new MessageResponse();
		res.setMessage("Food Deleted Successfully");

		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Food> updateFoodAvailability(@PathVariable Long id, 
			                                                      @RequestHeader("Authorization") String jwt) throws Exception{
		User user = UserService.findUserByJwtToken(jwt);
		Food food = foodService.updateAvailability(id);

		return new ResponseEntity<>(food,HttpStatus.CREATED);
	}
	







}
