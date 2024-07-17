package Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.Category;
import Model.Food;
import Model.Resturant;
import Repository.FoodRepository;
import request.CreateFoodRequest;

@Service
public class FoodServiceImp implements FoodService {

	private static final Logger logger = LoggerFactory.getLogger(FoodServiceImp.class);

	@Autowired
	private FoodRepository foodRepository;

	@Override
	public Food CreateFood(CreateFoodRequest req, Category category, Resturant Resturant) {
		logger.debug("Creating food: {}", req.getName());

		Food food = new Food();
		food.setFoodcategory(category);
		food.setResturant(Resturant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngrediensts(req.getIngredients());
		food.setSeasonal(req.isSeasonal());
		food.setVegetarian(req.isVegetarian());

		Food savedFood = foodRepository.save(food);
		Resturant.getFoods().add(savedFood);

		logger.info("Food created successfully: {}", savedFood.getName());
		return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		logger.debug("Deleting food with ID: {}", foodId);

		Food food = findFoodById(foodId);
		food.setResturant(null);
		foodRepository.save(food);

		logger.info("Food deleted successfully with ID: {}", foodId);
	}

	@Override
	public List<Food> getResturantsFood(long resturantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal,
			String foodCategory) {
		logger.debug(
				"Fetching restaurant's foods with filters: vegetarian={}, non-vegetarian={}, seasonal={}, category={}",
				isVegetarian, isNonVeg, isSeasonal, foodCategory);

		List<Food> foods = foodRepository.findResturantById(resturantId);

		if (isVegetarian) {
			foods = filterByVegetarian(foods, isVegetarian);
		}
		if (isNonVeg) {
			foods = filterByNonVeg(foods, isNonVeg);
		}
		if (isSeasonal) {
			foods = filterBySeasonal(foods, isSeasonal);
		}
		if (foodCategory != null && !foodCategory.equals("")) {
			foods = filterByCategory(foods, foodCategory);
		}

		logger.info("Fetched {} foods for restaurant ID: {}", foods.size(), resturantId);
		return foods;
	}

	private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
		return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
	}

	private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
		return foods.stream().filter(food -> {
			if (food.getFoodcategory() != null) {
				return food.getFoodcategory().getName().equals(foodCategory);
			}
			return false;
		}).collect(Collectors.toList());
	}

	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
		return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());
	}

	private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
		return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String keyword) {
		logger.debug("Searching for foods with keyword: {}", keyword);
		List<Food> foods = foodRepository.searchFood(keyword);
		logger.info("Found {} foods matching keyword: {}", foods.size(), keyword);
		return foods;
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		logger.debug("Fetching food details for ID: {}", foodId);
		Optional<Food> optionalFood = foodRepository.findById(foodId);
		if (optionalFood.isEmpty()) {
			logger.error("Food item not found with ID: {}", foodId);
			throw new Exception("Food item doesn't exist for ID: " + foodId);
		}
		Food food = optionalFood.get();
		logger.info("Food fetched successfully for ID: {}", foodId);
		return food;
	}

	@Override
	public Food updateAvailability(Long foodId) throws Exception {
		logger.debug("Updating availability for food with ID: {}", foodId);
		Food food = findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		Food updatedFood = foodRepository.save(food);
		logger.info("Availability updated successfully for food with ID: {}", foodId);
		return updatedFood;
	}

}
