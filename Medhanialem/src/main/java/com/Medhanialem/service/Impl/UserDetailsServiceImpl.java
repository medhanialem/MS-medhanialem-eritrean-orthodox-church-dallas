package com.Medhanialem.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Medhanialem.exception.UserNotActiveException;
import com.Medhanialem.jwtauthentication.model.User;
import com.Medhanialem.repository.UserRepository;
import com.Medhanialem.service.UserPrinciple;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		if(!user.isActive()) {
			throw new UserNotActiveException("User Account", "username", user.getUsername());
		}
		return UserPrinciple.build(user);
	}
}
