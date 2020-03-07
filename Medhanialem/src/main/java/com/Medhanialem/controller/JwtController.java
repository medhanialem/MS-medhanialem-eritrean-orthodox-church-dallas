package com.Medhanialem.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.jwtauthentication.model.JwtResponse;
import com.Medhanialem.jwtauthentication.model.LoginForm;
import com.Medhanialem.jwtauthentication.model.ResponseMessage;
import com.Medhanialem.jwtauthentication.model.Role;
import com.Medhanialem.jwtauthentication.model.RoleName;
import com.Medhanialem.jwtauthentication.model.Signup;
import com.Medhanialem.jwtauthentication.model.User;
import com.Medhanialem.jwtauthentication.security.jwt.JwtProvider;
import com.Medhanialem.repository.RoleRepository;
import com.Medhanialem.repository.UserRepository;

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
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "secretary":
				Role secretaryRole = roleRepository.findByName(RoleName.SECRETARY)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(secretaryRole);

				break;

			case "sunday school":
				Role sundaySchoolRole = roleRepository.findByName(RoleName.SUNDAY_SCHOOL)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(sundaySchoolRole);

				break;
			case "sebeka gubae":
				Role sebekaGubaeRole = roleRepository.findByName(RoleName.SEBEKA_GUBAE)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(sebekaGubaeRole);

				break;
				
			case "abo wenber sebeka gubae":
				Role aboWenberSebekaGubaeRole = roleRepository.findByName(RoleName.ABO_WENBER_SEBEKA_GUBAE)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(aboWenberSebekaGubaeRole);

				break;
				
//			default:
//				Role memberRole = roleRepository.findByName(RoleName.ROLE_MEMBER)
//						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//				roles.add(memberRole);
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}


}
