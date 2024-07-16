package Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import Model.Category;
import Model.Resturant;
import Repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	private ResturantService resturantService;
	
	private CategoryService categoryService;
	
	private CategoryRepository categoryRepository;

	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		Resturant resturant = resturantService.getResturantByUserId(userId);
		Category category = new Category();
		category.setName(name);
		category.setResturant(resturant);
		
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryByResturantId(Long id) throws Exception {
		Resturant resturant = resturantService.getResturantByUserId(id);
		return categoryRepository.findByResturantId(resturant.getId());
	}

	@Override
	public Category findCategoryById(Long Id) throws Exception {
		Optional<Category> category = categoryRepository.findById(Id);
		if(category.isEmpty())
		{
			throw new Exception("Category not found...");
		}
		
		return category.get();
	}

}
