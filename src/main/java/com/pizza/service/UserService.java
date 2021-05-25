package com.pizza.service;

import com.pizza.common.*;
import com.pizza.dao.UserDao;
import com.pizza.model.entity.Province;
import com.pizza.model.entity.User;
import com.pizza.model.input.RegisterInput;
import com.pizza.model.output.UserListOutput;
import com.pizza.repository.ProvinceRepository;
import com.pizza.repository.RoleRepository;
import com.pizza.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UserService {
    public static final String ERROR = "error";
    public static final String ATTRIBUTE_CITIES = "cities";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private UserDao userDao;

    public String pageLogin(Model model) {
        model.addAttribute(ERROR, null);
        return PageConstant.PAGE_LOGIN;
    }

    public String pageRegister(Model model) {
        model.addAttribute(ERROR, null);
        model.addAttribute(ATTRIBUTE_CITIES, provinceRepository.findDistinctCity());
        return PageConstant.PAGE_REGISTER;
    }

    public String getUserList(Model model) {
        model.addAttribute("users", userDao.getListUser(1, ""));
        model.addAttribute(ATTRIBUTE_CITIES, provinceRepository.findDistinctCity());
        return PageConstant.PAGE_USER_LIST;
    }

    @Transactional(rollbackOn = Exception.class)
    public String register(RegisterInput userInput) {
        String result = "";
        StringBuilder address = new StringBuilder();
        User user;
        try {
            if (userInput.getId() > 0) {
                // step 2: check email exists
                if (!ObjectUtils
                        .isEmpty(userRepository.findByUserNameAndIdNot(userInput.getEmail(), userInput.getId()))) {
                    return MessageConstant.EMAIL_EXIST;
                }

                user = userRepository.findById(userInput.getId()).orElse(new User());

                // convert from register input to user entity
                user.setFullname(userInput.getFullname());
                user.setPhone(userInput.getPhone());
                user.setUserName(userInput.getEmail());
                user.setRole(roleRepository.findById(userInput.getRole()).orElse(null));
            } else {
                // step 2: check email exists
                if (!ObjectUtils.isEmpty(userRepository.findByUserName(userInput.getEmail()))) {
                    return MessageConstant.EMAIL_EXIST;
                }
                // convert from register input to user entity
                user = ConvertEntity.convertToUser(userInput);
                user.setRole(roleRepository.findById(userInput.getRole()).orElse(null));
                user.setPassword(BCrypt.hashpw(userInput.getPassword(), BCrypt.gensalt(12)));
                user.setCreateDate(new Date());
                user.setStatus(Constant.STATUS_ENABLE);
            }

            Province province = provinceRepository.findById(userInput.getWard()).orElse(null);
            address.append(userInput.getAddress());
            if (!ObjectUtils.isEmpty(province)) {
                address.append(" ");
                address.append(province.getWardName());
                address.append(" ");
                address.append(province.getDistrictName());
                address.append(" ");
                address.append(province.getCityName());
            }
            user.setAddress(address.toString());
            user.setWard(userInput.getWard());

            // step 3: save
            userRepository.save(user);

            // gui mail
            if (userInput.isUserRegist()) {
                sendMailService.sendMailRigistSuccess(user.getUserName(), user.getFullname());
            }
        } catch (Exception e) {
            result = MessageConstant.RIGISTER_ERROR;
        }
        return result;
    }

    public User getUserInfo(int id) {
        User user = userRepository.findById(id).orElse(new User());
        Province province = provinceRepository.findById(user.getWard()).orElse(null);
        if (!ObjectUtils.isEmpty(province)) {
            String address = user.getAddress()
                    .replace(" " + province.getWardName(), "")
                    .replace(" " + province.getDistrictName(), "")
                    .replace(" " + province.getCityName(), "");
            user.setAddress(address);
        }
        user.setProvince(province);
        return user;
    }

    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElse(new User());
        userRepository.delete(user);
    }

    public UserListOutput userListAjax(int page, String keyword) {
        return userDao.getListUser(page, keyword);
    }
}
