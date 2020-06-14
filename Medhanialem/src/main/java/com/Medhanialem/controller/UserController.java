
package com.Medhanialem.controller;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.jwtauthentication.model.User;
import com.Medhanialem.jwtauthentication.security.jwt.JwtProvider;
import com.Medhanialem.model.PasswordDto;
import com.Medhanialem.repository.RoleRepository;
import com.Medhanialem.repository.UserRepository;
import com.Medhanialem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	UserService userService;

	@Autowired
	private UserRepository userRepository;

	//@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ABO_WENBER_SEBEKA_GUBAE')")
	public List<User> getAllUsers() {
		logger.info("Inside getAllUsers() method, {}", logger.getName());
		 return userRepository.findAll();
	 }

//	//this API is called for profile page for user
//	@GetMapping("profile")
//	@PreAuthorize("isAuthenticated()")
//	public ResponseEntity<?> updateUser(@RequestParam String username) {
//
//		UserDetails userDetail = userService.getCurrentUserDetails();
//		if(null==userDetail){
//			throw new BackendException("Fail! -> Cause: No session for username!");
//		}
//		if(!(userDetail.getUsername().equals(username))){
//			throw new BackendException("Fail! -> Cause: requested Username not the same as logged in user!");
//		}
//		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//	}

	//this API changes a password for a logged in user
	@PostMapping("/user/updatePassword")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> changeUserPassword( @Valid @RequestBody PasswordDto passwordDto) {
		User user =null;

		if(null!=userService.getCurrentUserDetails()) {
			user = userService.getUserByUsername(userService.getCurrentUserDetails().getUsername());
		}

		if (!userService.checkIfValidConfirmPassword(passwordDto)) {
			throw new RuntimeException(" Password and Confirm Pa!");
		}

		if (!(userService.checkIfValidOldPassword(user, passwordDto.getOldPassword()))) {
			throw new RuntimeException("Incorrect Password!");
		}

		userService.changeUserPassword(user, passwordDto.getNewPassword());
		return new ResponseEntity<>("Password Updated Successfully!", HttpStatus.OK);
	}

}