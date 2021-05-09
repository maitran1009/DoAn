package com.pizza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pizza.model.entity.OrderDetail;
import com.pizza.model.entity.ProductDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	List<OrderDetail> findByProductDetail(ProductDetail productDetail);

	@Query("SELECT o FROM OrderDetail o Where o.order.id = :id")
	List<OrderDetail> findByOrder(int id);
}
