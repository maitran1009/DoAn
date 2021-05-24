package com.pizza.common;

import com.pizza.model.entity.OrderDetail;
import com.pizza.model.entity.ProductDetail;
import com.pizza.model.entity.User;
import com.pizza.model.input.RegisterInput;
import com.pizza.model.output.OrderDetailOutput;
import com.pizza.model.output.ProductDetailOutput;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class ConvertEntity {
    /**
     * Chức năng: lấy dữ liệu từ input đổ vào db
     *
     * @param input RegisterInput
     * @return User
     */
    public static User convertToUser(RegisterInput input) {
        User user = null;
        if (!ObjectUtils.isEmpty(input)) {
            user = new User();
            user.setFullname(input.getFullname());
            user.setPhone(input.getPhone());
            user.setUserName(input.getEmail());
        }
        return user;
    }

    public static ProductDetailOutput convertToProductDetail(ProductDetail detail) {
        ProductDetailOutput output = new ProductDetailOutput();
        output.setId(detail.getId());
        output.setSize(detail.getSize().getId());
        output.setSizeName(detail.getSize().getName());
        output.setStatus(detail.getStatus());
        if (detail.getStatus() == 1) {
            output.setStatusName("Còn hàng");
        } else {
            output.setStatusName("Hết hàng");
        }
        return output;
    }

    public static List<ProductDetailOutput> convertToProductDetailList(List<ProductDetail> details) {
        List<ProductDetailOutput> detailOutputs = new ArrayList<>();
        for (ProductDetail detail : details) {
            detailOutputs.add(convertToProductDetail(detail));
        }
        return detailOutputs;
    }

    public static OrderDetailOutput convertToOrderDetailOutput(OrderDetail orderDetail) {
        OrderDetailOutput output = new OrderDetailOutput();
        if (!ObjectUtils.isEmpty(orderDetail)) {
            output.setProductName(orderDetail.getProductDetail().getProduct().getName());
            output.setSizeName(orderDetail.getProductDetail().getSize().getName());
            output.setQuantity(orderDetail.getQuantity());
        }
        return output;
    }

    public static List<OrderDetailOutput> convertToOrderDetailOutputList(List<OrderDetail> orderDetails) {
        List<OrderDetailOutput> outputs = new ArrayList<>();
        for (OrderDetail detail : orderDetails) {
            outputs.add(convertToOrderDetailOutput(detail));
        }
        return outputs;
    }
}
