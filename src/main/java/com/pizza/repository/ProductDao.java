package com.pizza.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.pizza.common.ConvertEntity;
import com.pizza.model.entity.Product;
import com.pizza.model.output.Pagination;
import com.pizza.model.output.ProductListOutput;
import com.pizza.model.output.ProductOutput;

@Repository
public class ProductDao {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@SuppressWarnings({ "deprecation", "unchecked" })
	public ProductListOutput getListProduct(int page, String keyword) {
		ProductListOutput products = new ProductListOutput();
		List<ProductOutput> productOutputs = new ArrayList<>();
		List<ProductOutput> outputs = new ArrayList<>();
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

		productOutputs = query.getResultList();
		for (ProductOutput productOutput : productOutputs) {
			productOutput.setProductDetail(ConvertEntity
					.convertToProductDetailList(productDetailRepository.findByProduct(productOutput.getId())));
			outputs.add(productOutput);
		}

		products.setProducts(outputs);
		products.setPagination(pagination);

		return products;
	}

	@SuppressWarnings("deprecation")
	private String createSqlQuery(String keyword, boolean flag) {
		StringBuilder sql = new StringBuilder();

		if (flag) {
			sql.append("Select new ");
			sql.append(ProductOutput.class.getName());
			sql.append(" (p.id, p.name, p.image, p.price, p.description) ");
		} else {
			sql.append("Select count(*) ");
		}

		sql.append(" From ");
		sql.append(Product.class.getName());// get tên class
		sql.append(" p ");

		if (!StringUtils.isEmpty(keyword)) {
			sql.append(" Where ");
			sql.append(" p.name like :keyword ");
		}

		if (flag) {
			sql.append(" Order by p.id Desc ");
		}

		return sql.toString();
	}
}
