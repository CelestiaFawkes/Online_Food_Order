package Service;

import java.util.List;
import java.util.Optional;

import Model.IngredientCategory;
import Model.IngredientsItems;
import Model.Resturant;
import Repository.IngredientCategoryRepository;
import Repository.IngredientItemRepository;

public class IngredientServiceImp implements IngredientsService{
	
	private IngredientItemRepository ingredientItemRepository;
	
	private IngredientCategoryRepository ingredientCategoryRepository;
	
	private ResturantService resturantService;

	@Override
	public IngredientCategory createIngredientCategory(String name, Long resturantId) throws Exception {
		Resturant resturant =resturantService.findResturantById(resturantId);
		IngredientCategory category = new IngredientCategory();
		category.setResturant(resturant);
		category.setName(name);
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientCategory findIngredientById(Long Id) throws Exception {
		Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(Id);
		if(opt.isEmpty())
		{
			throw new Exception("ingredient Category not found");
		}
		return opt.get();
	}

	@Override
	public List<IngredientCategory> findResturantByIngredientcategory(Long id) throws Exception {
		resturantService.findResturantById(id);
		
		return ingredientCategoryRepository.findByResturantId(id);
	}

	@Override
	public IngredientsItems createIngredientItems(Long resturantid, String name, Long categoryId) throws Exception {
		Resturant resturant =resturantService.findResturantById(resturantid);
		IngredientCategory category = findIngredientById(categoryId);
		IngredientsItems items = new IngredientsItems();
		items.setName(name);
		items.setResturant(resturant);
		items.setCategory(category);
		
		IngredientsItems ingredient = ingredientItemRepository.save(items);
		category.getIngredients().add(ingredient);
		
		
		
		return ingredient;
	}

	@Override
	public List<IngredientsItems> findResturantIngredients(Long resturantId) {
		return ingredientItemRepository.findResturantById(resturantId);
	}

	@Override
	public IngredientsItems updateStock(Long id) throws Exception {
		Optional<IngredientsItems> optional = ingredientItemRepository.findById(id);
		if(optional.isEmpty())
		{
			throw new Exception("ingredient not found..");
		}
		
		IngredientsItems ingredientItems = optional.get();
		ingredientItems.setInStock(!ingredientItems.isInStock());
		return ingredientItemRepository.save(ingredientItems);
	}

}
