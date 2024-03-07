
package in.cody.expensetrackerapi.controller;

import in.cody.expensetrackerapi.entity.AuthModel;
import in.cody.expensetrackerapi.entity.JwtResponse;
import in.cody.expensetrackerapi.entity.User;
import in.cody.expensetrackerapi.security.UserDetailsServiceImpl;
import in.cody.expensetrackerapi.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

//import in.cody.expensetrackerapi.entity.User;
import in.cody.expensetrackerapi.entity.UserModel;
import in.cody.expensetrackerapi.service.UserService;
import jakarta.validation.Valid;

@RestController
public class AuthController {

	private final UserService userService;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;

	public AuthController(UserService userService, UserDetailsServiceImpl userDetailsService, AuthenticationManager authenticationManager,JwtTokenUtil jwtTokenUtil ) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
		this.authenticationManager= authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil ;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(
			@Valid @RequestBody UserModel userModel) {
		return new ResponseEntity<>(userService.createUser(userModel), HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> loginDetails(@RequestBody AuthModel authLogin) throws Exception {

		authenticate(authLogin.getEmail(), authLogin.getPassword());

		//we need to generate the jwt token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authLogin.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad Credentials");
		}

	}
}
