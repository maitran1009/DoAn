package com.pizza.common;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
				result += cart.getPrice() * cart.getCount();
			}
		}
		return result;
	}

	public static String randomStringNumber(int length) {
		String strNumber = "0123456789";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < length) { // length of the random string.
			salt.append(strNumber.charAt(rnd.nextInt(10)));
		}
		return salt.toString();
	}
	
	public static String convertDateToString(String format, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
}
