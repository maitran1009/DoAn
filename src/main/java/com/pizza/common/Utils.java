package com.pizza.common;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
}
