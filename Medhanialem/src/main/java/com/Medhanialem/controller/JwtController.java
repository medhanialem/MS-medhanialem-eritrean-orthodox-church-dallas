package com.Medhanialem.controller;

import com.Medhanialem.exception.InvalidRequestException;
import com.Medhanialem.jwtauthentication.model.*;
import com.Medhanialem.jwtauthentication.security.jwt.JwtProvider;
import com.Medhanialem.repository.RoleRepository;
import com.Medhanialem.repository.UserRepository;
import com.Medhanialem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class JwtController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginform) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginform.getUsername(), loginform.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@PostMapping("/signup")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ABO_WENBER_SEBEKA_GUBAE')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody Signup signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getUsername(), 
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ADMIN)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not found."));
				roles.add(adminRole);

				break;
			case "secretary":
				Role secretaryRole = roleRepository.findByName(RoleName.SECRETARY)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not find."));
				roles.add(secretaryRole);

				break;

			case "sunday school":
				Role sundaySchoolRole = roleRepository.findByName(RoleName.SUNDAY_SCHOOL)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not find."));
				roles.add(sundaySchoolRole);

				break;
			case "sebeka gubae":
				Role sebekaGubaeRole = roleRepository.findByName(RoleName.SEBEKA_GUBAE)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not find."));
				roles.add(sebekaGubaeRole);

				break;
				
			case "abo wenber sebeka gubae":
				Role aboWenberSebekaGubaeRole = roleRepository.findByName(RoleName.ABO_WENBER_SEBEKA_GUBAE)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not find."));
				roles.add(aboWenberSebekaGubaeRole);

				break;
				
//			default:
//				Role memberRole = roleRepository.findByName(RoleName.ROLE_MEMBER)
//						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//				roles.add(memberRole);
			}
		});
		if(null!=userService.getCurrentUserDetails()) {
			user.setCreatedBy(userService.getCurrentUserDetails().getUsername());
		}
		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
	
	@PostMapping("/updateUser")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'ABO_WENBER_SEBEKA_GUBAE')")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUser updateUserRequest) {
		
		User user = userRepository.getOne(updateUserRequest.getId());
		if (null == user) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> the User you are trying to edit doesnot exist in Database!"),
					HttpStatus.BAD_REQUEST);
		}

		Set<String> strRoles = updateUserRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ADMIN)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "secretary":
				Role secretaryRole = roleRepository.findByName(RoleName.SECRETARY)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not find."));
				roles.add(secretaryRole);

				break;

			case "sunday school":
				Role sundaySchoolRole = roleRepository.findByName(RoleName.SUNDAY_SCHOOL)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not find."));
				roles.add(sundaySchoolRole);

				break;
			case "sebeka gubae":
				Role sebekaGubaeRole = roleRepository.findByName(RoleName.SEBEKA_GUBAE)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not find."));
				roles.add(sebekaGubaeRole);

				break;
				
			case "abo wenber sebeka gubae":
				Role aboWenberSebekaGubaeRole = roleRepository.findByName(RoleName.ABO_WENBER_SEBEKA_GUBAE)
						.orElseThrow(() -> new InvalidRequestException("Fail! -> Cause: User Role not find."));
				roles.add(aboWenberSebekaGubaeRole);

				break;
			}
		});
		
		if(null!=userService.getCurrentUserDetails()) {
			user.setUpdatedBy(userService.getCurrentUserDetails().getUsername());
		}
		
		user.setRoles(roles);
		user.setActive(updateUserRequest.isActive());
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User updated successfully!"), HttpStatus.OK);
	}


}
