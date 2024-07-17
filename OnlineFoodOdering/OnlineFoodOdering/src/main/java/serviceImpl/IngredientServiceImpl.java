package serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.IngredientCategory;
import Model.IngredientsItems;
import Model.Resturant;
import Repository.IngredientCategoryRepository;
import Repository.IngredientItemRepository;
import Service.IngredientsService;
import Service.ResturantService;

@Service
public class IngredientServiceImpl implements IngredientsService {

	private static final Logger logger = LoggerFactory.getLogger(IngredientServiceImpl.class);

	@Autowired
	private IngredientItemRepository ingredientItemRepository;

	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;

	@Autowired
	private ResturantService resturantService;

	@Override
	public IngredientCategory createIngredientCategory(String name, Long resturantId) throws Exception {
		logger.debug("Creating ingredient category '{}' for restaurant ID: {}", name, resturantId);

		Resturant resturant = resturantService.findResturantById(resturantId);
		IngredientCategory category = new IngredientCategory();
		category.setResturant(resturant);
		category.setName(name);

		IngredientCategory savedCategory = ingredientCategoryRepository.save(category);

		logger.info("Ingredient category '{}' created successfully", name);
		return savedCategory;
	}

	@Override
	public IngredientCategory findIngredientById(Long Id) throws Exception {
		logger.debug("Fetching ingredient category with ID: {}", Id);

		Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(Id);
		if (opt.isEmpty()) {
			logger.error("Ingredient category not found with ID: {}", Id);
			throw new Exception("Ingredient category not found");
		}
		IngredientCategory category = opt.get();

		logger.info("Ingredient category fetched successfully with ID: {}", Id);
		return category;
	}

	@Override
	public List<IngredientCategory> findResturantByIngredientcategory(Long id) throws Exception {
		logger.debug("Fetching ingredient categories for restaurant with ID: {}", id);

		resturantService.findResturantById(id);
		List<IngredientCategory> categories = ingredientCategoryRepository.findByResturantId(id);

		logger.info("Found {} ingredient categories for restaurant with ID: {}", categories.size(), id);
		return categories;
	}

	@Override
	public IngredientsItems createIngredientItems(Long resturantid, String name, Long categoryId) throws Exception {
		logger.debug("Creating ingredient item '{}' for restaurant ID: {} and category ID: {}", name, resturantid,
				categoryId);

		Resturant resturant = resturantService.findResturantById(resturantid);
		IngredientCategory category = findIngredientById(categoryId);

		IngredientsItems items = new IngredientsItems();
		items.setName(name);
		items.setResturant(resturant);
		items.setCategory(category);

		IngredientsItems savedIngredient = ingredientItemRepository.save(items);
		category.getIngredients().add(savedIngredient);

		logger.info("Ingredient item '{}' created successfully", name);
		return savedIngredient;
	}

	@Override
	public List<IngredientsItems> findResturantIngredients(Long resturantId) {
		logger.debug("Fetching ingredients items for restaurant with ID: {}", resturantId);

		List<IngredientsItems> items = ingredientItemRepository.findResturantById(resturantId);

		logger.info("Found {} ingredients items for restaurant with ID: {}", items.size(), resturantId);
		return items;
	}

	@Override
	public IngredientsItems updateStock(Long id) throws Exception {
		logger.debug("Updating stock availability for ingredient with ID: {}", id);

		Optional<IngredientsItems> optional = ingredientItemRepository.findById(id);
		if (optional.isEmpty()) {
			logger.error("Ingredient item not found with ID: {}", id);
			throw new Exception("Ingredient item not found");
		}

		IngredientsItems ingredientItems = optional.get();
		ingredientItems.setInStock(!ingredientItems.isInStock());
		IngredientsItems updatedItem = ingredientItemRepository.save(ingredientItems);

		logger.info("Stock availability updated successfully for ingredient with ID: {}", id);
		return updatedItem;
	}

}
