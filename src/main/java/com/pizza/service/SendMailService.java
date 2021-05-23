package com.pizza.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.pizza.common.Utils;
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

    private static final String PAY_SUCCESS_EMAIL_TEMPLATE = "pay-success-email-template";
    private static final String PAY_CONFIRM_EMAIL_TEMPLATE = "pay-confirm-email-template";
    private static final String REGIST_SUCCESS_EMAIL_TEMPLATE = "regist-success-email-template";
    public static final String STATIC_IMAGES_LOGO_1_GIF = "static/images/logo1.gif";
    public static final String FULL_NAME = "fullName";
    private static final String EMAIL_LOGO = "logo";
    public static final String TITLE = "title";

    public Boolean sendMailPaySuccess(Order order, List<OrderDetail> orderDetails, boolean flag) {
        try {
            Map<String, Object> templateVariables = new HashMap<>();
            Context context = new Context();
            Product product;
            ProductDetail productDetail;
            int i = 0;
            String title = "Bạn đã đặt 1 đơn hàng của cửa hàng MySu Food";
            String subject = "Thông báo đặt hàng thành công tại MySu Food";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            templateVariables.put(FULL_NAME, order.getFullname());
            templateVariables.put("size", orderDetails.size());
            templateVariables.put("orderId", order.getId());

            if (!flag) {
                title = "Đơn hàng đã bị huỷ";
            }
            templateVariables.put(TITLE, title);

            for (OrderDetail orderDetail : orderDetails) {
                productDetail = orderDetail.getProductDetail();
                i++;
                templateVariables.put("productName" + i, productDetail.getProduct().getName());
                templateVariables.put("sizeName" + i, productDetail.getSize().getName());
                templateVariables.put("quantity" + i, orderDetail.getQuantity());
                templateVariables.put("amount" + i,
                        Utils.currencyMoney((int) (orderDetail.getQuantity() * productDetail.getProduct().getPrice())));
            }

            // Thymleaf context
            context.setVariables(templateVariables);
            String html = templateEngine.process(PAY_SUCCESS_EMAIL_TEMPLATE, context);

            helper.setTo(order.getEmail());
            helper.setText(html, true);
            helper.addInline(EMAIL_LOGO, new ClassPathResource(STATIC_IMAGES_LOGO_1_GIF), MediaType.IMAGE_GIF_VALUE);

            i = 0;
            for (OrderDetail orderDetail : orderDetails) {
                product = orderDetail.getProductDetail().getProduct();
                i++;

                helper.addInline("image" + i, new ClassPathResource("static/" + product.getImage()), MediaType.IMAGE_GIF_VALUE);
            }

            if (!flag) {
                subject = "Thông báo huỷ đơn hàng tại MySu Food";
            }

            helper.setSubject(subject);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean sendMailPayConfirm(String email, String fullName, String code) {
        try {
            Map<String, Object> templateVariables = new HashMap<>();
            Context context = new Context();
            String title = "Bạn đã đặt 1 đơn hàng của cửa hàng MySu Food";
            String subject = "Thông báo xác thực đặt hàng tại MySu Food";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            templateVariables.put(FULL_NAME, fullName);
            templateVariables.put("code", code);
            templateVariables.put(TITLE, title);

            // Thymleaf context
            context.setVariables(templateVariables);
            String html = templateEngine.process(PAY_CONFIRM_EMAIL_TEMPLATE, context);

            helper.setTo(email);
            helper.setText(html, true);
            helper.addInline(EMAIL_LOGO, new ClassPathResource(STATIC_IMAGES_LOGO_1_GIF), MediaType.IMAGE_GIF_VALUE);

            helper.setSubject(subject);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean sendMailRigistSuccess(String email, String fullName) {
        try {
            Map<String, Object> templateVariables = new HashMap<>();
            Context context = new Context();
            String title = "Bạn đã đăng ký thành công tài khoản cửa hàng MySu Food";
            String subject = "Thông báo đăng ký tài khoản thành công tại MySu Food";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            templateVariables.put(FULL_NAME, fullName);
            templateVariables.put(TITLE, title);

            // Thymleaf context
            context.setVariables(templateVariables);
            String html = templateEngine.process(REGIST_SUCCESS_EMAIL_TEMPLATE, context);

            helper.setTo(email);
            helper.setText(html, true);
            helper.addInline(EMAIL_LOGO, new ClassPathResource(STATIC_IMAGES_LOGO_1_GIF), MediaType.IMAGE_GIF_VALUE);

            helper.setSubject(subject);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }

}
