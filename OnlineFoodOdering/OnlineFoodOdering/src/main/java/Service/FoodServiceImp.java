package Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.Category;
import Model.Food;
import Model.Resturant;
import Repository.FoodRepository;
import request.CreateFoodRequest;

@Service
public class FoodServiceImp implements FoodService{
	
	@Autowired
	private FoodRepository foodRepository;

	@Override
	public Food CreateFood(CreateFoodRequest req, Category category, Resturant Resturant) {
		Food food = new Food();
		food.setFoodcategory(category);
		food.setResturant(Resturant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngrediensts(req.getIngredients());
		food.setSeasonal(req.isSeasonal());
		food.setVegetarian(req.isVegetarian());;
		Food savedFood =  foodRepository.save(food);
		Resturant.getFoods().add(savedFood);
		
		return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setResturant(null);
		foodRepository.save(food);
		
	}

	@Override
	public List<Food> getResturantsFood(long resturantId,
			                            boolean isVegetarian, 
			                            boolean isNonVeg, 
			                            boolean isSeasonal,
			                            String foodCategory) {
		
		List<Food> foods = foodRepository.findResturantById(resturantId);
		if(isVegetarian)
		{
			foods = filterByVegetarian(foods,isVegetarian);
		}
		if(isNonVeg)
		{
			foods = filterByNonVeg(foods,isNonVeg);
		}

		if(isSeasonal)
		{
			foods = filterBySeasonal(foods,isSeasonal);
		}
		if(foodCategory !=null && !foodCategory.equals(""))
		{
			foods= filterByCategory(foods,foodCategory);
		}

		
		return foods;
	}

	private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
		return foods.stream().filter(food->food.isVegetarian() == false).collect(Collectors.toList());

	}

	private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
		return foods.stream().filter(food ->{
			if(food.getFoodcategory()!=null)
			{
				return food.getFoodcategory().getName().equals(foodCategory);
			}
			return false;
				
		}
				).collect(Collectors.toList());
	}

	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
		return foods.stream().filter(food->food.isSeasonal() == isSeasonal).collect(Collectors.toList());

	}

	private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
		return foods.stream().filter(food->food.isVegetarian() == isVegetarian).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String keyword) {
		
		return foodRepository.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		Optional<Food> optionalFood = foodRepository.findById(foodId);
		if(optionalFood.isEmpty())
		{
			throw new Exception("Food item doesnt exist...");
		}
		
		return optionalFood.get();
	}

	@Override
	public Food updateAvailability(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		return foodRepository.save(food);
		
	}

}
