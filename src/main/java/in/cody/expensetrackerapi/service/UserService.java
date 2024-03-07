package in.cody.expensetrackerapi.service;

import in.cody.expensetrackerapi.entity.User;
import in.cody.expensetrackerapi.entity.UserModel;

public interface UserService {
	
	User createUser(UserModel user);
	
	User readUser(Long id);
	
	User updateUser(UserModel user, Long id);
	
	void deleteUser(Long id);

	User getLoggedInUser();

	

}
