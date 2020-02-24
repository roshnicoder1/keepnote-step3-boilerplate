package com.stackroute.keepnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.dao.UserDAO;
import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

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
public class UserServiceImpl implements UserService {
	/*
	 * Autowiring should be implemented for the userDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */
	@Autowired
     private UserDAO userDAO;
	/*
	 * This method should be used to save a new user.
	 */
 @Override
	public boolean registerUser(User user) throws UserAlreadyExistException {
		if(userDAO.registerUser(user))
			return true;
		else
			throw new UserAlreadyExistException("Error");
	}
	/*
	 * This method should be used to update a existing user.
	 */
 @Override
	public User updateUser(User user, String userId) throws Exception {
		User ifalive = getUserById(userId);
		if(ifalive != null)
		{
		userDAO.updateUser(user);
		return user;
	}
	else
		return null;
}
	/*
	 * This method should be used to get a user by userId.
	 */
 @Override
 public User getUserById(String UserId) throws UserNotFoundException {
     User userObj = userDAO.getUserById(UserId);
     if(userObj != null)
         return userObj;
     else
         throw new UserNotFoundException("Error");
 }
	/*
	 * This method should be used to validate a user using userId and password.
	 */
 @Override
	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		if(userDAO.validateUser(userId, password))
			return true;
		else
			throw new UserNotFoundException("Error");
	}
	/* This method should be used to delete an existing user. */
 @Override
	public boolean deleteUser(String UserId) {
		return userDAO.deleteUser(UserId);
	}
}