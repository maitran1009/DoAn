package com.pizza.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import com.pizza.common.Constant;
import com.pizza.common.PageConstant;
import com.pizza.common.Utils;
import com.pizza.config.VNPAYConfig;
import com.pizza.model.base.Environment;
import com.pizza.model.entity.Order;
import com.pizza.model.entity.OrderDetail;
import com.pizza.model.entity.Province;
import com.pizza.model.input.PayInput;
import com.pizza.model.output.Cart;
import com.pizza.momo.model.CaptureMoMoResponse;
import com.pizza.momo.service.CaptureMoMo;
import com.pizza.repository.OrderDetailRepository;
import com.pizza.repository.OrderRepository;
import com.pizza.repository.ProductDetailRepository;
import com.pizza.repository.ProvinceRepository;

@Service
public class PayService {
	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private SendMailService sendMailService;

	@SuppressWarnings("unchecked")
	public String pagePay(HttpSession session, Model model) {
		String result = PageConstant.PAGE_PAY;
		List<Cart> carts = (List<Cart>) session.getAttribute(Constant.SESSION_CART);
		if (ObjectUtils.isEmpty(carts)) {
			result = PageConstant.REDIRECT_GIO_HANG;
		}
		model.addAttribute("cities", provinceRepository.findDistinctCity());
		model.addAttribute("totalMoney", Utils.currencyMoney((int) Utils.amount(carts)));
		model.addAttribute(Constant.AMOUNT, Utils.currencyMoney((int) Utils.amount(carts) + 15000));
		return result;
	}

	@SuppressWarnings("unchecked")
	public String pagePayType(HttpSession session, Model model) {
		String result = PageConstant.PAGE_PAY_TYPE;
		List<Cart> carts = (List<Cart>) session.getAttribute(Constant.SESSION_CART);
		if (ObjectUtils.isEmpty(carts)) {
			result = PageConstant.REDIRECT_GIO_HANG;
		}
		model.addAttribute("totalMoney", Utils.currencyMoney((int) Utils.amount(carts)));
		model.addAttribute(Constant.AMOUNT, Utils.currencyMoney((int) Utils.amount(carts) + 15000));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackOn = Exception.class)
	public boolean createPay(HttpSession session, PayInput input) {
		boolean result = false;
		try {
			List<Cart> carts = (List<Cart>) session.getAttribute(Constant.SESSION_CART);
			List<OrderDetail> orderDetails = new ArrayList<>();
			Order order = new Order();
			int amount = 0;

			for (Cart cart : carts) {
				amount += cart.getCount() * cart.getPrice();
			}

			Province province = provinceRepository.findById(Integer.valueOf(input.getWard())).get();

			order.setAddress(input.getAddress() + " " + province.getWardName() + " " + province.getDistrictName() + " "
					+ province.getCityName());
			order.setCreateDate(new Date());
			order.setAmount(amount);
			order.setEmail(input.getEmail());
			order.setFullname(input.getName());
			order.setPhone(input.getPhone());
			order.setStatus(1);

			order = orderRepository.save(order);

			for (Cart cart : carts) {
				OrderDetail orderDetail = new OrderDetail();

				orderDetail.setOrder(order);
				orderDetail.setQuantity(cart.getCount());
				orderDetail.setProductDetail(productDetailRepository.findById(cart.getProductDetailId()).get());

				orderDetails.add(orderDetail);
			}

			orderDetails = orderDetailRepository.saveAll(orderDetails);

			if (sendMailService.sendMailPaySuccess(order, orderDetails, true)) {
				result = true;
			}
			
			session.removeAttribute(Constant.SESSION_CART);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String payByVnPay(HttpServletRequest request, String bankCode, int amount) {
		try {
			Map<String, String> vnp_Params = new HashMap<>();
			vnp_Params.put("vnp_Version", VNPAYConfig.vnp_Version);
			vnp_Params.put("vnp_Command", VNPAYConfig.vnp_Command);
			vnp_Params.put("vnp_TmnCode", VNPAYConfig.vnp_TmnCode);
			vnp_Params.put("vnp_Amount", "10000");
			vnp_Params.put("vnp_CurrCode", VNPAYConfig.vnp_CurrCode);
			vnp_Params.put("vnp_BankCode", "NCB");
			vnp_Params.put("vnp_TxnRef", VNPAYConfig.getRandomNumber(8));
			vnp_Params.put("vnp_OrderInfo", VNPAYConfig.vnp_OrderInfo);
			vnp_Params.put("vnp_OrderType", VNPAYConfig.order_type);
			vnp_Params.put("vnp_Locale", "vn");
			vnp_Params.put("vnp_ReturnUrl", VNPAYConfig.vnp_Returnurl);
			vnp_Params.put("vnp_IpAddr", VNPAYConfig.vnp_IpAddr);
			vnp_Params.put("vnp_Merchant", VNPAYConfig.vnp_Merchant);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			vnp_Params.put("vnp_CreateDate", formatter.format(new Date()));

			// Build data to hash and querystring
			List fieldNames = new ArrayList(vnp_Params.keySet());
			Collections.sort(fieldNames);
			StringBuilder hashData = new StringBuilder();
			StringBuilder query = new StringBuilder();
			Iterator itr = fieldNames.iterator();
			while (itr.hasNext()) {
				String fieldName = (String) itr.next();
				String fieldValue = (String) vnp_Params.get(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					// Build hash data
					hashData.append(fieldName);
					hashData.append('=');
					hashData.append(fieldValue);
					// Build query
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

					if (itr.hasNext()) {
						query.append('&');
						hashData.append('&');
					}
				}
			}
			String queryUrl = query.toString();
			String vnp_SecureHash = VNPAYConfig.Sha256(VNPAYConfig.vnp_HashSecret + hashData.toString());
			queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
			System.out.println(VNPAYConfig.vnp_PayUrl + "?" + queryUrl);
			return VNPAYConfig.vnp_PayUrl + "?" + queryUrl;
		} catch (Exception e) {
			return null;
		}
	}

	public String getUrlPayMomo(PayInput input, HttpSession session) {
		// call api momo get pay url
		Environment environment = Environment.selectEnv("dev", Environment.ProcessType.PAY_GATE);
		
		String requestId = String.valueOf(System.currentTimeMillis());
		String orderId = String.valueOf(System.currentTimeMillis());
		
		CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(environment, orderId, requestId, input.getAmount(), orderId,
				Constant.RETURN_URL, Constant.NOTIFY_URL, "merchantName=MySu Food");

		// case captureMoMoResponse is null or empty
		if (ObjectUtils.isEmpty(captureMoMoResponse)) {
			System.out.println("CaptureMoMoResponse is null or empty");
		}
		session.setAttribute(Constant.SESSION_PAY_INPUT, input);
		return captureMoMoResponse.getPayUrl();
	}
	
}
