package Service;

import java.util.List;

import org.springframework.stereotype.Service;

import Model.Category;
import Model.Food;
import Model.Resturant;
import request.CreateFoodRequest;

@Service

public interface FoodService {

	public Food CreateFood(CreateFoodRequest req, Category category, Resturant Rresturant);

	void deleteFood(Long foodId) throws Exception;

	public List<Food> getResturantsFood(long resturantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal,
			String foodCategory);

	public List<Food> searchFood(String keyword);

	public Food findFoodById(Long foodId) throws Exception;

	public Food updateAvailability(Long foodId) throws Exception;

}
