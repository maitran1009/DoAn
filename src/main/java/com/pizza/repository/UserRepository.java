package com.pizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizza.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUserNameAndPassword(String userName, String password);
	
	User findByUserName(String userName);//ktra tồn tại của email nhập vào có tồn tại trong db hay ko
}
