package com.pizza.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.pizza.model.entity.User;
import com.pizza.model.output.Pagination;
import com.pizza.model.output.UserListOutput;

@Repository
public class UserDao {
	@Autowired
	private EntityManager entityManager;

	@SuppressWarnings({ "deprecation", "unchecked" })
	public UserListOutput getListUser(int page, String keyword) {
		UserListOutput response = new UserListOutput();
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

		response.setPagination(pagination);
		response.setUsers(query.getResultList());

		return response;
	}

	@SuppressWarnings("deprecation")
	private String createSqlQuery(String keyword, boolean flag) {
		StringBuilder sql = new StringBuilder();

		if (flag) {
			sql.append("Select u ");
		} else {
			sql.append("Select count(*) ");
		}

		sql.append(" From ");
		sql.append(User.class.getName());// get tên class
		sql.append(" u ");

		if (!StringUtils.isEmpty(keyword)) {
			sql.append(" Where ");
			sql.append(" u.fullname like :keyword ");
			sql.append(" OR u.userName like :keyword ");
		}

		if (flag) {
			sql.append(" Order by u.id Desc ");
		}

		return sql.toString();
	}
}
