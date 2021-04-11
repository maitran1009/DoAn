package com.pizza.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import com.pizza.common.PageConstant;
import com.pizza.common.Utils;
import com.pizza.model.entity.Product;
import com.pizza.model.entity.Size;
import com.pizza.model.output.Cart;
import com.pizza.repository.ProductDetailRepository;
import com.pizza.repository.ProductRepository;
import com.pizza.repository.SizeRepository;

@Service
public class CartService {
	private static final String REDIRECT_GIO_HANG = "redirect:/gio-hang";
	private static final String AMOUNT = "amount";
	private static final String TOTAL = "total";
	private static final String SESSION_CART = "carts";

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SizeRepository sizeRepository;

	@SuppressWarnings("unchecked")
	public String pageCart(Model model, HttpSession session) {
		List<Cart> carts = (List<Cart>) session.getAttribute(SESSION_CART);//LẤY ra ds các sản phẩm ở trong giỏ hàng ở session
		int total = carts == null ? 0 : carts.size();//total: đếm số lượng sản phẩm
		model.addAttribute(TOTAL, total);
		model.addAttribute(AMOUNT, Utils.currencyMoney((int) Utils.amount(carts)));//amount để tính tổng số tiền trong giỏ hàng
		return PageConstant.PAGE_CART;
	}

	@SuppressWarnings("unchecked")
	public String addToCart(Model model, HttpSession session, int id, int sizeId) {
		try {
			List<Cart> carts = (List<Cart>) session.getAttribute(SESSION_CART);
			Cart cart = new Cart();
			boolean flag = false;
			int cartId = 1;
			int index = 0;
			int total;

			// kiem tra neu carts null thì khởi tạo danh sách 
			if (ObjectUtils.isEmpty(carts)) {
				carts = new ArrayList<>();
			} else {
				// kiêm tra xem sản pham đã có trong giỏ hàng chưa
				for (Cart item : carts) {
					if (item.getProductId() == id && item.getSizeId() == sizeId) {
						cart = item;
						flag = true;
						index++;
						break;
					}
				}
				cartId = carts.get(carts.size() - 1).getId() + 1;
			}
			if (flag) {
				cart.setCount(cart.getCount() + 1);
				carts.set(index - 1, cart);
			} else {
				Product product = productRepository.findById(id).get();

				Size size = sizeRepository.findById(sizeId).get();

				cart.setId(cartId);
				cart.setCount(1);
				cart.setProductId(id);
				cart.setSizeId(sizeId);
				cart.setProductDetail(productDetailRepository.findByProductAndSize(product, size));

				carts.add(cart);
			}
			total = carts == null ? 0 : carts.size();

			session.setAttribute(SESSION_CART, carts);
			model.addAttribute(TOTAL, total);
			model.addAttribute(AMOUNT, Utils.currencyMoney((int) Utils.amount(carts)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return REDIRECT_GIO_HANG;
	}

	@SuppressWarnings("unchecked")
	public String updateToCart(Model model, HttpSession session, int id, int flag) {
		try {
			List<Cart> carts = (List<Cart>) session.getAttribute(SESSION_CART);
			int index = 0;
			int count;
			if (!ObjectUtils.isEmpty(carts)) {
				for (Cart cart : carts) {
					if (cart.getId() == id) {
						if (flag == 0) {
							count = cart.getCount() - 1 == 0 ? 1 : cart.getCount() - 1;
							cart.setCount(count);
						} else {
							count = cart.getCount() + 1 > 5 ? 5 : cart.getCount() + 1;
							cart.setCount(count);
						}
						carts.set(index, cart);
						break;
					}
					index++;
				}
			}

			int total = carts == null ? 0 : carts.size();
			session.setAttribute(SESSION_CART, carts);
			model.addAttribute(TOTAL, carts.size());
			model.addAttribute(AMOUNT, Utils.currencyMoney((int) Utils.amount(carts)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return REDIRECT_GIO_HANG;
	}

	@SuppressWarnings("unchecked")
	public String deleteToCart(Model model, HttpSession session, int id) {
		try {
			List<Cart> carts = (List<Cart>) session.getAttribute(SESSION_CART);
			int total;

			if (!ObjectUtils.isEmpty(carts)) {
				carts.forEach(cart -> {
					if (cart.getId() == id) {
						carts.remove(cart);
					}
				});
			}
			total = carts == null ? 0 : carts.size();

			session.setAttribute(SESSION_CART, carts);
			model.addAttribute(TOTAL, carts.size());
			model.addAttribute(AMOUNT, Utils.currencyMoney((int) Utils.amount(carts)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return REDIRECT_GIO_HANG;
	}

}
