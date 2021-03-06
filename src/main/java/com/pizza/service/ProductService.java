package com.pizza.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.pizza.common.PageConstant;
import com.pizza.model.entity.OrderDetail;
import com.pizza.model.entity.Product;
import com.pizza.model.entity.ProductDetail;
import com.pizza.model.input.ProductDetailInput;
import com.pizza.model.input.ProductInput;
import com.pizza.model.output.ProductOutput;
import com.pizza.repository.OrderDetailRepository;
import com.pizza.repository.ProductDetailRepository;
import com.pizza.repository.ProductRepository;
import com.pizza.repository.SizeRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SizeRepository sizeRepository;

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	public String productList(Model model) {
		model.addAttribute("products", productRepository.findAll());
		return PageConstant.PAGE_PRODUCT_LIST;
	}

	@SuppressWarnings("deprecation")
	@Transactional(rollbackOn = Exception.class)
	public boolean create(ProductInput input) {
		boolean result = false;
		try {
			Product product = new Product();
			List<ProductDetail> detailAdd = new ArrayList<>();
			List<ProductDetail> detailUpdate = new ArrayList<>();
			List<ProductDetail> detailDelete = new ArrayList<>();
			List<OrderDetail> orderDetails;
			List<Integer> detailId = new ArrayList<>();
			boolean flag = true;

			if (input.getId() > 0) {
				product.setId(input.getId());
				detailUpdate = productRepository.findById(input.getId()).get().getProductDetails();
			}
			product.setName(input.getName());
			product.setDescription(input.getDescription());
			product.setPrice(input.getPrice());
			if (!StringUtils.isEmpty(input.getImage()))
				product.setImage("/images/" + input.getImage());

			product = productRepository.save(product);

			for (ProductDetailInput item : input.getProductDetail()) {
				ProductDetail detail = new ProductDetail();
				detail.setProduct(product);
				detail.setSize(sizeRepository.findById(item.getSize()).get());
				detail.setStatus(item.getStatus());

				// ki???m tra c?? t???o m???i
				if (item.getId() == 0) {
					detailAdd.add(detail);
				} else {
					detail.setId(item.getId());

					// t??m ki???m m???t ph???n t??? trong m???ng
					for (ProductDetail value : detailUpdate) {
						if (value.getId() == detail.getId()) {
							flag = true;
							detailId.add(value.getId());
						}
					}

					// update
					if (flag) {
						detailAdd.add(detail);
					} else {
						// n???u ko c?? th?? b??? xo??
						detailDelete.add(detail);
					}
				}
			}

			for (ProductDetail value : detailUpdate) {
				if (!detailId.contains(value.getId())) {
					// n???u ko c?? th?? b??? xo??
					detailDelete.add(value);
				}
			}

			// th??m m???i v?? update
			if (!ObjectUtils.isEmpty(detailAdd))
				productDetailRepository.saveAll(detailAdd);

			// x??a
			if (!ObjectUtils.isEmpty(detailDelete)) {
				for (ProductDetail detail : detailDelete) {
					// x??? l?? x??a t???t c??? nh??ng th???ng chi ti???t ????n h??ng c?? li??n k???t v???i chi ti???t s???n
					// ph???m
					orderDetails = orderDetailRepository.findByProductDetail(detail);
					orderDetailRepository.deleteAll(orderDetails);
				}
				productDetailRepository.deleteAll(detailDelete);
			}

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional(rollbackOn = Exception.class)
	public boolean deleteProduct(int id) {
		boolean result = false;
		try {
			Product product = productRepository.findById(id).get();
			List<OrderDetail> orderDetails = null;
			List<ProductDetail> productDetails = product.getProductDetails();

			for (ProductDetail productDetail : productDetails) {
				orderDetails = orderDetailRepository.findByProductDetail(productDetail);
				orderDetailRepository.deleteAll(orderDetails);
			}

			productDetailRepository.deleteAll(productDetails);

			productRepository.delete(product);

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ProductOutput getProductInfo(int id) {
		return new ProductOutput().convertTo(productRepository.findById(id).get());
	}
}
