package com.demo.token.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.demo.token.model.Users;
import com.demo.token.repo.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImp(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Users users = userRepository.findByuserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("UserName not found"));
		return new CustomUserDetails(users);

		// return userRepository.findByuserName(userName).orElseThrow(()->new
		// UsernameNotFoundException("UserName not found"));
	}

}
