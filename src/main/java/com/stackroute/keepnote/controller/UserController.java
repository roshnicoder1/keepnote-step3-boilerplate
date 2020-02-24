package com.stackroute.keepnote.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class UserController {

	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	@Autowired
	private UserService userService;
	@Autowired
	HttpSession session;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping(value = "/")
	public String welcomemsg() {
		return "Welcome !!!";
	}
	
	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in a User table
	 * in the database. This handler method should return any one of the status
	 * messages basis on different situations: 1. 201(CREATED) - If the user created
	 * successfully. 2. 409(CONFLICT) - If the userId conflicts with any existing
	 * user
	 * 
	 * Note: ------ This method can be called without being logged in as well as
	 * when a new user will use the app, he will register himself first before
	 * login.
	 * 
	 * This handler method should map to the URL "/user/register" using HTTP POST
	 * method
	 */
//	@RequestMapping(value = "/user/register", method = RequestMethod.POST, produces = "application/json")
	
	@PostMapping("/user/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		
		try {
			userService.registerUser(user);
		} catch (UserAlreadyExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.CONFLICT); 
		}	
		headers.add("User registered  - ", String.valueOf(user.getUserId()));
		return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
	}

	/*
	 * Define a handler method which will update a specific user by reading the
	 * Serialized object from request body and save the updated user details in a
	 * user table in database handle exception as well. This handler method should
	 * return any one of the status messages basis on different situations: 1.
	 * 200(OK) - If the user updated successfully. 2. 404(NOT FOUND) - If the user
	 * with specified userId is not found. 3. 401(UNAUTHORIZED) - If the user trying
	 * to perform the action has not logged in.
	 * 
	 * This handler method should map to the URL "/user/{id}" using HTTP PUT method.
	 */
	
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
//	@PutMapping("/user/{id}")
//	public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) throws UserNotFoundException {
//		HttpHeaders headers = new HttpHeaders();
//		User isExist = userService.getUserById(id);
//		if (isExist == null) {
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//		} else if (session.getAttribute("loggedInuser")==null) {
//			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
//		}
//		userService.updateUser(user, id);
//		headers.add("User Updated  - ", id);
//		return new ResponseEntity<User>(user, headers, HttpStatus.OK);
//	}
	
	@PutMapping(value="/user/{userId}")
	public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable("userId") String userId,HttpServletRequest request) throws UserNotFoundException
	{
		System.out.println("user id : "+userId);
		HttpHeaders headers = new HttpHeaders();
		try {
			String loggedInUser =(String) request.getSession().getAttribute("loggedInUserId");
			if(loggedInUser== null)
			{
				return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
			}
			if(userService.updateUser(user, userId)!=null)
			{
			    return new ResponseEntity<>(headers, HttpStatus.OK);
			}
		} catch (UserAlreadyExistException e) {
			e.printStackTrace();
			 return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			 return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		    return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
	}

	/*
	 * Define a handler method which will delete a user from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 * 3. 401(UNAUTHORIZED) - If the user trying to perform the action has not
	 * logged in.
	 * 
	 * This handler method should map to the URL "/user/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid userId without {}
	 */
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
//	public ResponseEntity<User> deleteUser(@PathVariable("id") String userId) throws UserNotFoundException {
//		HttpHeaders headers = new HttpHeaders();
//		User user = userService.getUserById(userId);
//		if (user == null) {
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//		}
//	    userService.deleteUser(userId);
//		headers.add("User Deleted - ", String.valueOf(userId));
//		return new ResponseEntity<User>(user, headers, HttpStatus.NO_CONTENT);
//	}
	
	@DeleteMapping(value="/user/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId, HttpServletRequest request) throws UserNotFoundException
	{
		System.out.println("user id : "+userId);
		HttpHeaders headers = new HttpHeaders();
		try {
				String loggedInUser =(String) request.getSession().getAttribute("loggedInUserId");
				if(loggedInUser== null)
				{
					return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
				}
				if(userService.deleteUser(userId))
				{
					return new ResponseEntity<>(headers, HttpStatus.OK);
				}
			
		}  catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		    return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
	}
	/*
	 * Define a handler method which will show details of a specific user handle
	 * UserNotFoundException as well. This handler method should return any one of
	 * the status messages basis on different situations: 1. 200(OK) - If the user
	 * found successfully. 2. 401(UNAUTHORIZED) - If the user trying to perform the
	 * action has not logged in. 3. 404(NOT FOUND) - If the user with specified
	 * userId is not found. This handler method should map to the URL "/user/{id}"
	 * using HTTP GET method where "id" should be replaced by a valid userId without
	 * {}
	 */
	
//	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
//	public ResponseEntity<User> getUser(@PathVariable("id") String userId) throws UserNotFoundException {
//		User user = userService.getUserById(userId);
//		if (user == null) {
//			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<User>(user, HttpStatus.OK);
//	}
	@GetMapping(value="/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") String userId,HttpServletRequest request) throws UserNotFoundException
	{
		System.out.println("user id : "+userId);
		HttpHeaders headers = new HttpHeaders();
		try {
			String loggedInUser =(String) request.getSession().getAttribute("loggedInUserId");
			if(loggedInUser== null)
			{
				return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);
			}
				if(userService.getUserById(userId)!=null)
				{
					return new ResponseEntity<>(headers, HttpStatus.OK);
				}
			    
		}  catch (UserNotFoundException e) {
			e.printStackTrace();
		    return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
	}

}