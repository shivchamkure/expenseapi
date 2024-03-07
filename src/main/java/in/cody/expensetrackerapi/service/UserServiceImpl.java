package in.cody.expensetrackerapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.cody.expensetrackerapi.entity.User;
import in.cody.expensetrackerapi.entity.UserModel;
import in.cody.expensetrackerapi.exception.ItemAlreadyExistsException;
import in.cody.expensetrackerapi.exception.ResourceNotFoundException;
import in.cody.expensetrackerapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	UserRepository userRepository;

	PasswordEncoder bCryptEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder bCryptEncoder) {
		this.userRepository = userRepository;
		this.bCryptEncoder = bCryptEncoder;
	}

	@Override
	public User createUser(UserModel userModel) {
		
		if(userRepository.existsByEmail(userModel.getEmail())) {
			throw new ItemAlreadyExistsException("User already registered with email: "+ userModel.getEmail());
		}
		User newUser = new User();
		//Copy userModel object properties to newUser entity object
		BeanUtils.copyProperties(userModel, newUser);
		String encoded = bCryptEncoder.encode(newUser.getPassword());
		newUser.setPassword(encoded);
		return userRepository.save(newUser);
	}
	
	
	@Override
	public User readUser(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for the id " + id));
	}


	@Override
	public User updateUser(UserModel user, Long id) {
		User oUser = readUser(id);
		oUser.setName(user.getName() != null ? user.getName() : oUser.getName());
		oUser.setEmail(user.getEmail() != null ? user.getEmail() : oUser.getEmail());
		oUser.setPassword(user.getPassword() != null ? user.getPassword() : oUser.getPassword());
		oUser.setAge(user.getAge() != null ? user.getAge() : oUser.getAge());
		return userRepository.save(oUser);
	}


	@Override
	public void deleteUser(Long id) {
		User oUser = readUser(id);
		userRepository.delete(oUser);
	}

	@Override
	public User getLoggedInUser() {
	Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
	String email = authentication.getName();
	return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for the id " + email));

	}


}
