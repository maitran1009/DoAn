package com.pizza.model.input;

import java.io.Serializable;

public class LoginInput implements Serializable {
	private static final long serialVersionUID = 1L;

	private String email;
	private String password;
	private boolean remember;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}
}
