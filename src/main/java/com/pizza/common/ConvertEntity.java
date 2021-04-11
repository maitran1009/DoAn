package com.pizza.common;

import org.springframework.util.ObjectUtils;

import com.pizza.model.entity.User;
import com.pizza.model.input.RegisterInput;

public class ConvertEntity {
	/**
	 * Chức năng: lấy dữ liệu từ input đổ vào db
	 * @param input
	 * @return
	 */
	public static User convertToUser(RegisterInput input) {
		User user = null;
		if (!ObjectUtils.isEmpty(input)) {
			user = new User();
			user.setFullname(input.getFullname());
			user.setAddress(input.getAddress());
			user.setPhone(input.getPhone());
			user.setPassword(input.getPassword());
			user.setUserName(input.getEmail());
		}
		return user;
	}
}
