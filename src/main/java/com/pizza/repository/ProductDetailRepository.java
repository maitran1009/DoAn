package com.pizza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pizza.model.entity.Product;
import com.pizza.model.entity.ProductDetail;
import com.pizza.model.entity.Size;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
	ProductDetail findByProductAndSize(Product product, Size size);

	@Query("SELECT p FROM ProductDetail p Where p.product.id = :id")
	List<ProductDetail> findByProduct(int id);
}
