
package com.Medhanialem.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.exception.InvalidRequestException;
import com.Medhanialem.jwtauthentication.model.OperationStatusModel;
import com.Medhanialem.jwtauthentication.model.PasswordResetModel;
import com.Medhanialem.jwtauthentication.model.PasswordResetRequestModel;
import com.Medhanialem.jwtauthentication.model.RequestOperationName;
import com.Medhanialem.jwtauthentication.model.RequestOperationStatus;
import com.Medhanialem.jwtauthentication.model.User;
import com.Medhanialem.jwtauthentication.security.jwt.JwtProvider;
import com.Medhanialem.model.PasswordDto;
import com.Medhanialem.repository.RoleRepository;
import com.Medhanialem.repository.UserRepository;
import com.Medhanialem.service.UserService;

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

	// @CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ABO_WENBER_SEBEKA_GUBAE')")
	public List<User> getAllUsers() {
		logger.info("Inside getAllUsers() method, {}", logger.getName());
		return userRepository.findAll();
	}

//	//this API is called for profile page for user
	@GetMapping("profile")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> updateUser(@RequestParam String username) {

		UserDetails userDetail = userService.getCurrentUserDetails();
		if(null==userDetail){
			throw new BackendException("Fail! -> Cause: No session for username!");
		}
		if(!(userDetail.getUsername().equals(username))){
			throw new BackendException("Fail! -> Cause: requested Username not the same as logged in user!");
		}
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}

	// this API changes a password for a logged in user
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/user/updatePassword")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> changeUserPassword(@Valid @RequestBody PasswordDto passwordDto) {
		User user = null;

		if (null != userService.getCurrentUserDetails()) {
			user = userService.getUserByUsername(userService.getCurrentUserDetails().getUsername());
		}

		if (!userService.checkIfValidConfirmPassword(passwordDto)) {
			throw new InvalidRequestException(" Password and Confirm Password do not match!");
		}

		if (!(userService.checkIfValidOldPassword(user, passwordDto.getOldPassword()))) {
			throw new InvalidRequestException("Incorrect Password!");
		}

		userService.changeUserPassword(user, passwordDto.getNewPassword());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/password-reset-request")
	public ResponseEntity<OperationStatusModel> requestReset(
			@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
		OperationStatusModel returnValue = new OperationStatusModel();
		boolean operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());
		returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}

		// return returnValue;
		if (returnValue.getOperationResult().equalsIgnoreCase("SUCCESS")) {
			return new ResponseEntity<>(returnValue, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(returnValue, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/password-reset")
	public ResponseEntity<OperationStatusModel> requestReset(@RequestBody PasswordResetModel passwordResetModel) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (passwordResetModel.getEnterNewPassword().isEmpty()) {
			returnValue.setOperationResult(RequestOperationStatus.ENTER_NEW_PASSWORD_CANNOT_BE_EMPTY.name());
			//return returnValue;
			return new ResponseEntity<>(returnValue, HttpStatus.BAD_REQUEST);
		}

		if (passwordResetModel.getConfirmPassword().isEmpty()) {
			returnValue.setOperationResult(RequestOperationStatus.CONFIRM_PASSWORD_CANNOT_BE_EMPTY.name());
			//return returnValue;
			return new ResponseEntity<>(returnValue, HttpStatus.BAD_REQUEST);
		}

		if (passwordResetModel.getToken().isEmpty()) {
			returnValue.setOperationResult(RequestOperationStatus.MISSING_REQUIRED_TOKEN.name());
			//return returnValue;
			return new ResponseEntity<>(returnValue, HttpStatus.BAD_REQUEST);
		}

		if (!passwordResetModel.getEnterNewPassword().equals(passwordResetModel.getConfirmPassword())) {
			returnValue.setOperationResult(RequestOperationStatus.PASSWORDS_DO_NOT_MATCH.name());
			//return returnValue;
			return new ResponseEntity<>(returnValue, HttpStatus.BAD_REQUEST);
		}

		boolean operationResult = userService.resetPassword(passwordResetModel.getToken(),
				passwordResetModel.getEnterNewPassword());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}

		//return returnValue;
		if (returnValue.getOperationResult().equalsIgnoreCase("SUCCESS")) {
			return new ResponseEntity<>(returnValue, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(returnValue, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}