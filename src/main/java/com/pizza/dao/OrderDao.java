package com.pizza.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.pizza.model.entity.Order;
import com.pizza.model.output.OrderListOutput;
import com.pizza.model.output.OrderOutput;
import com.pizza.model.output.Pagination;
import com.pizza.repository.OrderDetailRepository;

@Repository
public class OrderDao {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@SuppressWarnings({ "deprecation", "unchecked" })
	public OrderListOutput getListOrder(int page, String keyword) {
		OrderListOutput response = new OrderListOutput();
		List<OrderOutput> orderOutputs = new ArrayList<>();
		List<OrderOutput> outputs = new ArrayList<>();
		Pagination pagination = new Pagination();
		pagination.setPage(page);

		// get string sql
		String sqlQuery = createSqlQuery(keyword, true);
		String sqlQueryTotal = createSqlQuery(keyword, false);

		Query query = entityManager.createQuery(sqlQuery).setFirstResult((page - 1) * pagination.getPageSize())
				.setMaxResults(pagination.getPageSize());
		Query queryTotal = entityManager.createQuery(sqlQueryTotal);

		// set parameter
		if (!StringUtils.isEmpty(keyword)) {
			query.setParameter("keyword", "%" + keyword + "%");
			queryTotal.setParameter("keyword", "%" + keyword + "%");
		}
		pagination.setTotal((long) queryTotal.getSingleResult());

		orderOutputs = query.getResultList();
		for (OrderOutput orderOutput : orderOutputs) {
			orderOutput.setOrderDetails(orderDetailRepository.findByOrder(orderOutput.getId()));
			outputs.add(orderOutput);
		}

		response.setOrders(outputs);
		response.setPagination(pagination);

		return response;
	}

	@SuppressWarnings("deprecation")
	private String createSqlQuery(String keyword, boolean flag) {
		StringBuilder sql = new StringBuilder();

		if (flag) {
			sql.append("Select new ");
			sql.append(OrderOutput.class.getName());
			sql.append(" (o.id, o.email, o.fullname, o.phone, o.address, o.status, o.amount, o.createDate) ");
		} else {
			sql.append("Select count(*) ");
		}

		sql.append(" From ");
		sql.append(Order.class.getName());// get tên class
		sql.append(" o ");

		if (!StringUtils.isEmpty(keyword)) {
			sql.append(" Where ");
			sql.append(" o.email like :keyword ");
			sql.append(" OR o.fullname like :keyword ");
		}

		if (flag) {
			sql.append(" Order by o.id Desc ");
		}

		return sql.toString();
	}
}
