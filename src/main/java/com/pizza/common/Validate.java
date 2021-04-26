package com.pizza.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.pizza.model.input.LoginInput;
import com.pizza.model.input.RegisterInput;

public class Validate {
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_FULL_NAME_REGEX = Pattern.compile("^[a-zA-Z]{3,30}$", Pattern.CASE_INSENSITIVE);

	public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^[a-zA-Z0-9]{6,}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_PHONE_REGEX = Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE);

	@SuppressWarnings("deprecation")
	public static boolean checkLogin(LoginInput user) {
		boolean flag = false;
		String email = user.getEmail();
		String password = user.getPassword();
		// case check email
		if (!StringUtils.isEmpty(email)) {
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
			if (matcher.find()) {
				// case password
				if (!StringUtils.isEmpty(password)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	@SuppressWarnings("deprecation")
	public static boolean checkRegister(RegisterInput user, boolean isAdmin) {
		boolean flag = true;
		String fullname = user.getFullname();
		String email = user.getEmail();
		String password = user.getPassword();
		String address = user.getAddress();
		String phone = user.getPhone();
		String rePassword = user.getRePassword();
		Matcher matcher;

		// Case check fullname
		matcher = VALID_FULL_NAME_REGEX.matcher(fullname);
		if (!matcher.find()) {
			return false;
		}
		// Case check email
		matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		if (!matcher.find()) {
			return false;
		}
		// Case check password
		matcher = VALID_PASSWORD_REGEX.matcher(password);
		if (!matcher.find()) {
			return false;
		}
		// Case check address
		if (StringUtils.isEmpty(address)) {
			return false;
		}
		// Case check phone
		matcher = VALID_PHONE_REGEX.matcher(phone);
		if (!matcher.find()) {
			return false;
		}
		// Case check rePassword
		if (!isAdmin && !rePassword.equals(password)) {
			return false;
		}
		return flag;

	}
}
