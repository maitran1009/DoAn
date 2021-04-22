package com.pizza.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.pizza.model.entity.Order;
import com.pizza.model.entity.OrderDetail;
import com.pizza.model.entity.Product;
import com.pizza.model.entity.ProductDetail;

@Service
public class SendMailService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	private static final String PAY_SUCCESS_EMAIL_TEMPLATE = "/mail/mail-pay-success";
	private static final String EMAIL_LOGO = "logo";

	public Boolean sendMailPaySuccess(Order order, List<OrderDetail> orderDetails) {
		try {
			Map<String, Object> templateVariables = new HashMap<>();
			Context context = new Context();
			Product product;
			ProductDetail productDetail;
			int i = 0;
			String path;

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

			templateVariables.put("fullName", order.getFullname());
			templateVariables.put("size", orderDetails.size());
			templateVariables.put("orderId", order.getId());

			for (OrderDetail orderDetail : orderDetails) {
				productDetail = orderDetail.getProductDetail();
				i++;
				templateVariables.put("productName" + i, productDetail.getProduct().getName());
				templateVariables.put("sizeName" + i, productDetail.getSize().getName());
				templateVariables.put("quantity" + i, orderDetail.getQuantity());
				templateVariables.put("amount" + i, orderDetail.getQuantity() * productDetail.getProduct().getPrice());
			}

			// Thymleaf context
			context.setVariables(templateVariables);
			String html = templateEngine.process(PAY_SUCCESS_EMAIL_TEMPLATE, context);

			helper.setTo(order.getEmail());
			helper.setText(html, true);
			helper.addInline(EMAIL_LOGO, new ClassPathResource("static/images/logo1.gif"), "image/gif");

			i = 0;
			for (OrderDetail orderDetail : orderDetails) {
				product = orderDetail.getProductDetail().getProduct();
				i++;

				helper.addInline("image" + i, new ClassPathResource("static/" + product.getImage()), "image/jpg");
			}

			helper.setSubject("Thông báo đặt hàng thành công tại MySu Food");

			javaMailSender.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return true;
	}

}
