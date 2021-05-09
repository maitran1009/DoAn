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
import com.pizza.dao.ProductDao;
import com.pizza.model.entity.OrderDetail;
import com.pizza.model.entity.Product;
import com.pizza.model.entity.ProductDetail;
import com.pizza.model.input.ProductDetailInput;
import com.pizza.model.input.ProductInput;
import com.pizza.model.output.ProductListOutput;
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

	@Autowired
	private ProductDao productDao;

	public String productList(Model model) {
		model.addAttribute("products", productDao.getListProduct(1, ""));
		return PageConstant.PAGE_PRODUCT_LIST;
	}

	public ProductListOutput productListAjax(Integer page, String keyword) {
		return productDao.getListProduct(page, keyword);
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

				// kiểm tra có tạo mới
				if (item.getId() == 0) {
					detailAdd.add(detail);
				} else {
					detail.setId(item.getId());

					// tìm kiếm một phần tử trong mảng
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
						// nếu ko có thì bị xoá
						detailDelete.add(detail);
					}
				}
			}

			for (ProductDetail value : detailUpdate) {
				if (!detailId.contains(value.getId())) {
					// nếu ko có thì bị xoá
					detailDelete.add(value);
				}
			}

			// thêm mới và update
			if (!ObjectUtils.isEmpty(detailAdd))
				productDetailRepository.saveAll(detailAdd);

			// xóa
			if (!ObjectUtils.isEmpty(detailDelete)) {
				for (ProductDetail detail : detailDelete) {
					// xử lý xóa tất cả nhưng thằng chi tiết đơn hàng có liên kết với chi tiết sản
					// phẩm
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
