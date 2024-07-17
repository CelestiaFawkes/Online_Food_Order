package controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(IngredientController.class);

	@Autowired
	private IngredientsService ingredientService;

	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req)
			throws Exception {
		logger.info("Creating ingredient category with name '{}' for restaurant ID: {}", req.getName(),
				req.getResturantId());
		IngredientCategory item = ingredientService.createIngredientCategory(req.getName(), req.getResturantId());
		return new ResponseEntity<>(item, HttpStatus.CREATED);
	}

	@PostMapping()
	public ResponseEntity<IngredientsItems> createIngredientItems(@RequestBody IngredientRequest req) throws Exception {
		logger.info("Creating ingredient item with name '{}' for restaurant ID: {} and category ID: {}", req.getName(),
				req.getResturantId(), req.getCategoryId());
		IngredientsItems item = ingredientService.createIngredientItems(req.getResturantId(), req.getName(),
				req.getCategoryId());
		return new ResponseEntity<>(item, HttpStatus.CREATED);
	}

	@PostMapping("{id}/stock")
	public ResponseEntity<IngredientsItems> updateingredientStock(@PathVariable Long id) throws Exception {
		logger.info("Updating stock for ingredient item with ID: {}", id);
		IngredientsItems item = ingredientService.updateStock(id);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@PostMapping("/resturant/{id}")
	public ResponseEntity<List<IngredientsItems>> getResturantIngredient(@PathVariable Long id) throws Exception {
		logger.info("Fetching ingredients for restaurant ID: {}", id);
		List<IngredientsItems> items = ingredientService.findResturantIngredients(id);
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@PostMapping("/resturant/{id}/category")
	public ResponseEntity<List<IngredientCategory>> getResturantIngredientCategory(@PathVariable Long id)
			throws Exception {
		logger.info("Fetching ingredient categories for restaurant ID: {}", id);
		List<IngredientCategory> items = ingredientService.findResturantByIngredientcategory(id);
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
}
