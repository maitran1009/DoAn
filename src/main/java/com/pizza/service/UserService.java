package com.pizza.service;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import com.pizza.common.Constant;
import com.pizza.common.ConvertEntity;
import com.pizza.common.MessageConstant;
import com.pizza.common.PageConstant;
import com.pizza.common.Validate;
import com.pizza.model.entity.User;
import com.pizza.model.input.LoginInput;
import com.pizza.model.input.RegisterInput;
import com.pizza.repository.UserRepository;

@Service
public class UserService {
	private static final String REDIRECT_DANG_NHAP = "redirect:/dang-nhap";
	@Autowired
	private UserRepository userRepository;

	public String pageLogin(Model model, HttpSession session, HttpServletRequest request) {
		session.removeAttribute("user");
		model.addAttribute("error", null);
		// Sử dụng cookie để lưu thông tin đăng nhập
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("email")) {
					model.addAttribute("email", cookie.getValue());
				}
				if (cookie.getName().equals("password")) {
					model.addAttribute("password", cookie.getValue());
				}
				if (cookie.getName().equals("remember")) {
					model.addAttribute("remember", cookie.getValue());
				}
			}
		}
		return PageConstant.PAGE_LOGIN;
	}

	public String login(Model model, HttpSession session, HttpServletResponse response, LoginInput userForm) {
		String result = PageConstant.PAGE_LOGIN;
		String message = null;
		try {
			if (Validate.checkLogin(userForm)) {
				User user = userRepository.findByUserName(userForm.getEmail());

				if (ObjectUtils.isEmpty(user)) {
					message = MessageConstant.LOGIN_ERROR;
				} else {
					if (!BCrypt.checkpw(userForm.getPassword(), user.getPassword())) {
						message = MessageConstant.LOGIN_ERROR;
					} else {
						if (userForm.isRemember()) {
							// create a cookie email
							Cookie cookieEmail = new Cookie("email", user.getUserName());
							cookieEmail.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days

							// create a cookie password
							Cookie cookiePassword = new Cookie("password", userForm.getPassword());
							cookiePassword.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days

							// remove a cookie remember
							Cookie cookieRemember = new Cookie("remember", "" + userForm.isRemember());
							cookieRemember.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days

							// add cookie to response
							response.addCookie(cookieEmail);
							response.addCookie(cookiePassword);
							response.addCookie(cookieRemember);
						} else {
							// remove a cookie email
							Cookie cookieEmail = new Cookie("email", null);
							cookieEmail.setMaxAge(0); // expires in 7 days

							// remove a cookie password
							Cookie cookiePassword = new Cookie("password", null);
							cookiePassword.setMaxAge(0); // expires in 7 days

							// remove a cookie remember
							Cookie cookieRemember = new Cookie("remember", null);
							cookieRemember.setMaxAge(0); // expires in 7 days

							// add cookie to response
							response.addCookie(cookieEmail);
							response.addCookie(cookiePassword);
							response.addCookie(cookieRemember);
						}
						// save session
						session.setAttribute("user", user);
						result = "redirect:/trang-chu";
					}
				}
			} else {
				message = MessageConstant.LOGIN_ERROR;
			}
		} catch (Exception e) {
			message = MessageConstant.LOGIN_ERROR;
		}
		model.addAttribute("error", message);
		return result;
	}

	public String pageLogout(Model model, HttpSession session, HttpServletResponse response) {
		session.removeAttribute("user");
		model.addAttribute("error", null);
		return REDIRECT_DANG_NHAP;
	}

	public String pageRegister(Model model, HttpSession session, HttpServletRequest request) {
		session.removeAttribute("user");
		model.addAttribute("error", null);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("email")) {
					model.addAttribute("email", cookie.getValue());
				}
				if (cookie.getName().equals("password")) {
					model.addAttribute("password", cookie.getValue());
				}
				if (cookie.getName().equals("remember")) {
					model.addAttribute("remember", cookie.getValue());
				}
			}
		}
		return PageConstant.PAGE_REGISTER;
	}

	@Transactional(rollbackOn = Exception.class)
	public String register(Model model, RegisterInput userInput) {
		String result = PageConstant.PAGE_REGISTER;
		String error = null;
		User user = null;
		try {
			// step 1: validate
			if (Validate.checkRegister(userInput)) {
				// step 2: check email exists
				if (ObjectUtils.isEmpty(userRepository.findByUserName(userInput.getEmail()))) {
					// convert from register input to user entity
					user = ConvertEntity.convertToUser(userInput);
					user.setStatus(Constant.STATUS_ENABLE);
					user.setCreateDate(new Date());
					user.setPassword(BCrypt.hashpw(userInput.getPassword(), BCrypt.gensalt(12)));

					// step 3: save
					userRepository.save(user);

					// step 4: redirect page login
					result = REDIRECT_DANG_NHAP;
				} else {
					error = MessageConstant.RIGISTER_ERROR;
				}
			} else {
				error = MessageConstant.RIGISTER_ERROR;
			}
		} catch (Exception e) {
			error = MessageConstant.RIGISTER_ERROR;
		}
		model.addAttribute("error", error);
		return result;
	}
}
