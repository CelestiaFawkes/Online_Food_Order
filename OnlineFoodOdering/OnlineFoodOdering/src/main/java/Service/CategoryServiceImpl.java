package Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.Category;
import Model.Resturant;
import Repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private ResturantService resturantService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		Resturant resturant = resturantService.getResturantByUserId(userId);
		Category category = new Category();
		category.setName(name);
		category.setResturant(resturant);

		Category savedCategory = categoryRepository.save(category);
		logger.info("Category {} created for restaurant {}", savedCategory.getName(), resturant.getId());
		return savedCategory;
	}

	@Override
	public List<Category> findCategoryByResturantId(Long id) throws Exception {
		Resturant resturant = resturantService.getResturantByUserId(id);
		List<Category> categories = categoryRepository.findByResturantId(resturant.getId());
		logger.info("Found {} categories for restaurant {}", categories.size(), resturant.getId());
		return categories;
	}

	@Override
	public Category findCategoryById(Long Id) throws Exception {
		Optional<Category> categoryOptional = categoryRepository.findById(Id);
		if (categoryOptional.isEmpty()) {
			throw new Exception("Category not found with id: " + Id);
		}
		Category category = categoryOptional.get();
		logger.info("Category {} found with id {}", category.getName(), Id);
		return category;
	}

}
