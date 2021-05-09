package com.pizza.model.output;

import java.util.List;

import com.pizza.model.entity.User;

public class UserListOutput {
	private Pagination pagination;
	private List<User> users;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
