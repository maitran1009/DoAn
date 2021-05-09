package com.pizza.service;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
import com.pizza.dao.UserDao;
import com.pizza.model.entity.Province;
import com.pizza.model.entity.User;
import com.pizza.model.input.RegisterInput;
import com.pizza.model.output.UserListOutput;
import com.pizza.repository.ProvinceRepository;
import com.pizza.repository.RoleRepository;
import com.pizza.repository.UserRepository;

@Service
public class UserService {
	private static final String REDIRECT_DANG_NHAP = "redirect:/dang-nhap";
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserDao userDao;

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
			if (Validate.checkRegister(userInput, false)) {
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

	public String getUserList(Model model) {
		model.addAttribute("users", userDao.getListUser(1, ""));
		model.addAttribute("cities", provinceRepository.findDistinctCity());
		return PageConstant.PAGE_USER_LIST;
	}

	@Transactional(rollbackOn = Exception.class)
	public String register(RegisterInput userInput) {
		String result = "ok";
		User user = null;
		try {
			// step 1: validate
//			if (Validate.checkRegister(userInput, true)) {

			if (userInput.getId() > 0) {
				// step 2: check email exists
				if (!ObjectUtils
						.isEmpty(userRepository.findByUserNameAndIdNot(userInput.getEmail(), userInput.getId()))) {
					return "Email đã tồn tại";
				}

				user = userRepository.findById(userInput.getId()).get();

				// convert from register input to user entity
				user.setFullname(userInput.getFullname());
				user.setPhone(userInput.getPhone());
				user.setUserName(userInput.getEmail());
				user.setRole(roleRepository.findById(userInput.getRole()).get());
				user.setAddress(userInput.getAddress());

			} else {
				// step 2: check email exists
				if (!ObjectUtils.isEmpty(userRepository.findByUserName(userInput.getEmail()))) {
					return "Email đã tồn tại";
				}
				// convert from register input to user entity
				user = ConvertEntity.convertToUser(userInput);
				user.setRole(roleRepository.findById(userInput.getRole()).get());
				user.setPassword(userInput.getPassword());
				user.setPassword(BCrypt.hashpw(userInput.getPassword(), BCrypt.gensalt(12)));
				user.setCreateDate(new Date());
				user.setStatus(Constant.STATUS_ENABLE);
				Province province = provinceRepository.findById(userInput.getWard()).get();
				user.setAddress(userInput.getAddress() + " " + province.getWardName() + " " + province.getDistrictName()
						+ " " + province.getCityName());
			}

			// step 3: save
			userRepository.save(user);
		} catch (Exception e) {
			result = MessageConstant.RIGISTER_ERROR;
		}
		return result;
	}

	public User getUserInfo(int id) {
		return userRepository.findById(id).get();
	}

	public void deleteUser(int id) {
		User user = userRepository.findById(id).get();
		userRepository.delete(user);
	}

	public UserListOutput userListAjax(int page, String keyword) {
		return userDao.getListUser(page, keyword);
	}
}
