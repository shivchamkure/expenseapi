package in.cody.expensetrackerapi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.cody.expensetrackerapi.entity.User;
import in.cody.expensetrackerapi.entity.UserModel;
import in.cody.expensetrackerapi.service.UserService;
import jakarta.validation.Valid;

@RestController
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> registerUser(@Valid @PathVariable("id") Long id) {
		return new ResponseEntity<>(userService.readUser(id), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> registerUser(@Valid @RequestBody UserModel userModel, @PathVariable("id") Long id) {
		return new ResponseEntity<>(userService.updateUser(userModel, id), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteUser(@Valid @PathVariable("id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
}