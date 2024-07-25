package Service;

import java.util.List;

import Model.IngredientCategory;
import Model.IngredientsItems;

public interface IngredientsService {
	
	public IngredientCategory createIngredientCategory(String name, Long resturantId) throws Exception;
	
	public IngredientCategory findIngredientById(Long Id) throws Exception;
	
	public List<IngredientCategory> findResturantByIngredientcategory(Long id)throws Exception;
	
	public IngredientsItems createIngredientItems(Long resturantid, String name, Long categoryId) throws Exception;
	
	public List<IngredientsItems> findResturantIngredients(Long resturantId);
	
	public IngredientsItems updateStock(Long id) throws Exception;
	
	
	

}
