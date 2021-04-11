package com.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pizza.model.output.MyUserDetails;
import com.pizza.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		com.pizza.model.entity.User user = userRepository.findByUserName(userName);

		if (ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
		}

//		// [ROLE_USER, ROLE_ADMIN,..]
//		List<String> roleNames = new ArrayList<>();
//		roleNames.add(user.getRole().getCode());
//
//		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//		if (roleNames != null) {
//			for (String role : roleNames) {
//				// ROLE_USER, ROLE_ADMIN,..
//				GrantedAuthority authority = new SimpleGrantedAuthority(role);
//				grantList.add(authority);
//			}
//		}
//
//		UserDetails userDetails = (UserDetails) new User(user.getUserName(), user.getPassword(), grantList);

		return new MyUserDetails(user);
	}

}