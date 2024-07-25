package controller;

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
	
	@Autowired
	private ResturantService resturantService;
	
	
	@Autowired
	private userService UserService;
	
	@PostMapping("/api/admin/create")
	public ResponseEntity<Resturant> createResturant(
			@RequestBody CreateResturantRequest req,
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		User user = UserService.findUserByJwtToken(jwt);
		Resturant resturant = resturantService.createResturant(req, user);
		
		
		return new ResponseEntity<>(resturant,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Resturant> updateResturant(
			@RequestBody CreateResturantRequest req,
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long Id
			) throws Exception{
		User user = UserService.findUserByJwtToken(jwt);
		Resturant resturant = resturantService.updateResturant(Id,req);
		
		
		return new ResponseEntity<>(resturant,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteResturant(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long Id
			) throws Exception{
		User user = UserService.findUserByJwtToken(jwt);
        resturantService.deleteResturant(Id);
        MessageResponse res = new MessageResponse();
        res.setMessage("Resturant deleted successfully..");
		
		
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<Resturant> updateResturantStatus(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long Id
			) throws Exception{
		User user = UserService.findUserByJwtToken(jwt);
		Resturant resturant = resturantService.updateResturantStatus(Id);
		
		
		return new ResponseEntity<>(resturant,HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<Resturant> findResturantBYUserId(
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		User user = UserService.findUserByJwtToken(jwt);
		Resturant resturant = resturantService.getResturantByUserId(user.getId());
		
		
		return new ResponseEntity<>(resturant,HttpStatus.OK);
		
	}




	

}
