package com.stackroute.keepnote.dao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */

@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	private SessionFactory sessionFactory;
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	
	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category)  {
		Session session = sessionFactory.getCurrentSession();
		session.save(category);
		session.flush();
		return true;
	}
		
	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId){
		 boolean flag = true;
			try {
				if(getCategoryById(categoryId)==null) {
					flag = false;
				}else {
					Session session = sessionFactory.getCurrentSession();
				      Category category= session.byId(Category.class).load(categoryId);
				      session.delete(category);
					  session.flush();
				}
			} catch (CategoryNotFoundException e) {
				e.printStackTrace();
//				throw new CategoryNotFoundException("CategoryNotFoundException");
			}
			return flag;
	}
	/*
	 * Update an existing category
	 */
	public boolean updateCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
	      Category cat = session.byId(Category.class).load(category.getCategoryId());
	      cat.setCategoryName(category.getCategoryName());
	      cat.setCategoryDescription(category.getCategoryDescription());
	      cat.setCategoryCreatedBy(category.getCategoryCreatedBy());
	      cat.setCategoryCreationDate(category.getCategoryCreatedDate());
	      session.flush();
	    return true;
	}
	/*
	 * Retrieve details of a specific category
	 */
	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Category cate= sessionFactory.getCurrentSession().find(Category.class, categoryId);
		if(cate==null) {
			throw new CategoryNotFoundException("CategoryNotFoundException");
		}
		else
			return cate;
		
	}
	/*
	 * Retrieve details of all categories by userId
	 */
//	public List<Category> getAllCategoryByUserId(String userId) {
//		Session s=sessionFactory.getCurrentSession();
//		List<Category> l= (List<Category>) s.get(Category.class, userId);
//		return l;
//	}
	
	public List<Category> getAllCategoryByUserId(String userId) {
		
	
		String str = "From Category category where categoryCreatedBy = :userId";
		Session s=sessionFactory.getCurrentSession();
		Query que = s.createQuery(str).setParameter("userId", userId);
		List ans = que.getResultList();
		return ans;
	}
}
//
//package com.stackroute.keepnote.dao;
//import java.util.List;
//import javax.persistence.Query;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import com.stackroute.keepnote.exception.CategoryNotFoundException;
//import com.stackroute.keepnote.model.Category;
//import com.stackroute.keepnote.model.User;
///*
// * This class is implementing the UserDAO interface. This class has to be annotated with 
// * @Repository annotation.
// * @Repository - is an annotation that marks the specific class as a Data Access Object, 
// * thus clarifying it's role.
// * @Transactional - The transactional annotation itself defines the scope of a single database 
// * 					transaction. The database transaction happens inside the scope of a persistence 
// * 					context.  
// * */
//@Repository
//@Transactional
//public class CategoryDAOImpl implements CategoryDAO {
//	/*
//	 * Autowiring should be implemented for the SessionFactory.(Use
//	 * constructor-based autowiring.
//	 */
//	@Autowired
//	private SessionFactory sessionFactory;
//	public CategoryDAOImpl(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
//	/*
//	 * Create a new category
//	 */
//	public boolean createCategory(Category category) {
//		Session session = sessionFactory.getCurrentSession();
//		session.save(category);
//		session.flush();
//		return true;
//	}
//	/*
//	 * Remove an existing category
//	 */
//	public boolean deleteCategory(int categoryId) {
//		boolean flag = true;
//		try {
//			if(getCategoryById(categoryId)==null) {
//				flag = false;
//			}else {
//				Session session = sessionFactory.getCurrentSession();
//				session.delete(getCategoryById(categoryId));
//				session.flush();
//			}
//		} catch (HibernateException e) {
//			e.printStackTrace();
//		} catch (CategoryNotFoundException e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//	/*
//	 * Update an existing category
//	 */
//	public boolean updateCategory(Category category) {
//		boolean flag = true;
//		try {
//			if(getCategoryById(category.getCategoryId())==null) {
//				flag = false;
//			}else {
//				sessionFactory.getCurrentSession().clear();
//				sessionFactory.getCurrentSession().update(category);
//				sessionFactory.getCurrentSession().flush();
//			}
//		} catch (HibernateException e) {
//			e.printStackTrace();
//		} catch (CategoryNotFoundException e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//	/*
//	 * Retrieve details of a specific category
//	 */
//	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
//		Session session = sessionFactory.getCurrentSession();
//		Category category = session.get(Category.class, categoryId);
//		if(category==null)
//			throw new CategoryNotFoundException("CategoryNotFoundException");
//		else {
//			session.flush();
//			return category;
//		}
//	}
//	/*
//	 * Retrieve details of all categories by userId
//	 */
//	public List<Category> getAllCategoryByUserId(String userId) {
//		String hql = "From Category category where categoryCreatedBy = :userId";
//		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId);
//		List result = query.getResultList();
//		return result;
//	}
//}