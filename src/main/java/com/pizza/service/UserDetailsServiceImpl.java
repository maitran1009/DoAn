package com.pizza.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.pizza.model.entity.User;
import com.pizza.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);

		if (ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
		/**
		 * GrantedAuthority: chứa danh sách các role: 1 nhân viên có nhiều quyền.
		 */
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();// khởi tạo mảng chứa các quyền của user
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));// thêm quyền vào mảng

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				grantedAuthorities);// chứa thông tin login
	}

}