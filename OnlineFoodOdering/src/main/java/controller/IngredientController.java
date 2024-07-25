package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.IngredientCategory;
import Model.IngredientsItems;
import Service.IngredientsService;
import request.IngredientCategoryRequest;
import request.IngredientRequest;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
	
	@Autowired
	private IngredientsService ingredientService;
	
	
	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngredientCategory(
			@RequestBody IngredientCategoryRequest req) throws Exception{
		IngredientCategory item = ingredientService.createIngredientCategory(req.getName(), req.getResturantId());
		return new ResponseEntity<>(item,HttpStatus.CREATED);
	}
	
	@PostMapping()
	public ResponseEntity<IngredientsItems> createIngredientItems(
			@RequestBody IngredientRequest req) throws Exception{
		IngredientsItems item = ingredientService.createIngredientItems(req.getResturantId(), req.getName(), req.getCategoryId());
		return new ResponseEntity<>(item,HttpStatus.CREATED);
	}
	
	@PostMapping("{id}/stock")
	public ResponseEntity<IngredientsItems> updateingredientStock(
			@PathVariable Long id) throws Exception{
		IngredientsItems item = ingredientService.updateStock(id);
		return new ResponseEntity<>(item,HttpStatus.OK);
	}
	
	@PostMapping("/resturant/{id}")
	public ResponseEntity<List<IngredientsItems>> getResturantIngredient(
			@PathVariable Long id) throws Exception{
		List<IngredientsItems> items = ingredientService.findResturantIngredients(id);
		return new ResponseEntity<>(items,HttpStatus.OK);
	}
	
	@PostMapping("/resturant/{id}/category")
	public ResponseEntity<List<IngredientCategory>> getResturantIngredientCategory(
			@PathVariable Long id) throws Exception{
		List<IngredientCategory> items = ingredientService.findResturantByIngredientcategory(id);
		return new ResponseEntity<>(items,HttpStatus.OK);
	}






}
