package com.pizza.common;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;

import com.pizza.model.output.Cart;

public class Utils {
	/**
	 * Định dạng sang tiền việt sử dụng hàm có sẵn trong java NumberFormat
	 * 
	 * @param money
	 * @return
	 */
	public static String currencyMoney(int money) {
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		return currencyVN.format(money);
	}

	/**
	 * Chức năng: Tính tổng tiền có trong giỏ hàng
	 */
	public static long amount(List<Cart> carts) {
		long result = 0;
		if (!ObjectUtils.isEmpty(carts)) {
			for (Cart cart : carts) {
				result += cart.getProductDetail().getProduct().getPrice() * cart.getCount();
			}
		}
		return result;
	}

	public static String toString(User user) {
		StringBuilder sb = new StringBuilder();

		sb.append("UserName:").append(user.getUsername());

		Collection<GrantedAuthority> authorities = user.getAuthorities();
		if (authorities != null && !authorities.isEmpty()) {
			sb.append(" (");
			boolean first = true;
			for (GrantedAuthority a : authorities) {
				if (first) {
					sb.append(a.getAuthority());
					first = false;
				} else {
					sb.append(", ").append(a.getAuthority());
				}
			}
			sb.append(")");
		}
		return sb.toString();
	}

	public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

}
