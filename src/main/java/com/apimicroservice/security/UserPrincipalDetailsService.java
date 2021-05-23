package com.apimicroservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apimicroservice.user.User;
import com.apimicroservice.user.UserRepository;

@Service
public class UserPrincipalDetailsService implements UserDetailsService{

	@Autowired
	UserRepository repository;
	
	/* Search the database by email and switch to UserDetails */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		MyUserDetails userPrincipal = new MyUserDetails(user);
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        return userPrincipal;		
		
	}	

}
