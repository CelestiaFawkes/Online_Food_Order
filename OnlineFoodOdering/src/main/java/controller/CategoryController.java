package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.Category;
import Model.User;
import Service.CategoryService;
import Service.userService;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	private userService UserService;
	
	
	@PostMapping("/admin/category")
	public ResponseEntity<Category> createCategory(@RequestBody Category category,
			                                       @RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = UserService.findUserByJwtToken(jwt);
		Category createdCategory = categoryService.createCategory(category.getName(), user.getId());
		return new ResponseEntity<>(createdCategory,HttpStatus.CREATED);
		
		
	}
	
	@GetMapping("/category/resturant")
	public ResponseEntity<List<Category>> getResturantCategory(
			                                       @RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = UserService.findUserByJwtToken(jwt);
		List<Category> categories = categoryService.findCategoryByResturantId(user.getId());
		return new ResponseEntity<>(categories,HttpStatus.OK);
		
		
	}

	
	

}
