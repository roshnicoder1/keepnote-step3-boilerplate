//package com.stackroute.keepnote.service;
//
//import java.util.List;
//import com.stackroute.keepnote.exception.CategoryNotFoundException;
//import com.stackroute.keepnote.model.Category;
//
///*
//* Service classes are used here to implement additional business logic/validation 
//* This class has to be annotated with @Service annotation.
//* @Service - It is a specialization of the component annotation. It doesn�t currently 
//* provide any additional behavior over the @Component annotation, but it�s a good idea 
//* to use @Service over @Component in service-layer classes because it specifies intent 
//* better. Additionally, tool support and additional behavior might rely on it in the 
//* future.
//* */
//
//public class CategoryServiceImpl implements CategoryService {
//	/*
//	 * Autowiring should be implemented for the CategoryDAO. (Use Constructor-based
//	 * autowiring) Please note that we should not create any object using the new
//	 * keyword.
//	 */
//
//	/*
//	 * This method should be used to save a new category.
//	 */
//	public boolean createCategory(Category category) {
//		return false;
//
//	}
//
//	/* This method should be used to delete an existing category. */
//	public boolean deleteCategory(int categoryId) {
//		return false;
//
//	}
//
//	/*
//	 * This method should be used to update a existing category.
//	 */
//
//	public Category updateCategory(Category category, int id) throws CategoryNotFoundException {
//		return category;
//
//	}
//
//	/*
//	 * This method should be used to get a category by categoryId.
//	 */
//	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
//		return null;
//
//	}
//
//	/*
//	 * This method should be used to get a category by userId.
//	 */
//
//	public List<Category> getAllCategoryByUserId(String userId) {
//		return null;
//
//	}
//
//}


package com.stackroute.keepnote.service;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	/*
	 * Autowiring should be implemented for the CategoryDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */
	@Autowired
    private CategoryDAO categoryDao;
	public CategoryServiceImpl(CategoryDAO categoryDao) {
		//super();
		this.categoryDao = categoryDao;
	}
	/*
	 * This method should be used to save a new category.
	 */
	public boolean createCategory(Category category) {
		boolean status=categoryDao.createCategory(category);
		return status;
	}
	/* This method should be used to delete an existing category. */
	public boolean deleteCategory(int categoryId) {
		boolean status=categoryDao.deleteCategory(categoryId);
		return status;
	}
	/*
	 * This method should be used to update a existing category.
	 */
	public Category updateCategory(Category category, int id) throws CategoryNotFoundException {
		category=getCategoryById(id);
		categoryDao.updateCategory(category);
		return category;
	}
	/*
	 * This method should be used to get a category by categoryId.
	 */
	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
			Category cate=categoryDao.getCategoryById(categoryId);
			if(cate==null) {
				throw new CategoryNotFoundException("CategoryNotFoundException");
			}
			else
				return cate;
	}
	/*
	 * This method should be used to get a category by userId.
	 */
	@Override
	public List<Category> getAllCategoryByUserId(String userId) {
		// TODO Auto-generated method stub
		List<Category> categories=categoryDao.getAllCategoryByUserId(userId);
		return categories;
	}
}
