package com.example.EpamSpringBoot.config.bruteforceprotect;

import com.example.EpamSpringBoot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserService")
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final com.example.EpamSpringBoot.user.User customer = customerRepository.findUserByUsername(username);
		if (customer == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetails user = User.withUsername(customer.getUsername())
			.password(customer.getPassword())
			.authorities("ROLE_TRAINEE", "ROLE_TRAINER")
			.build();
		return user;
	}

}