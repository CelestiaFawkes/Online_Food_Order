package Service;

import java.util.List;

import Model.Category;

public interface CategoryService {

	public Category createCategory(String name, Long userId) throws Exception;

	public List<Category> findCategoryByResturantId(Long id) throws Exception;

	public Category findCategoryById(Long Id) throws Exception;

}
