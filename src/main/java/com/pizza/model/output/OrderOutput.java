package com.pizza.model.output;

import java.util.Date;
import java.util.List;

import com.pizza.common.Constant;
import com.pizza.common.Utils;

public class OrderOutput {
	private int id;
	private String email;
	private String fullname;
	private String phone;
	private String address;
	private int status;
	@SuppressWarnings("unused")
	private String statusName;
	private int amount;
	private String createDate;
	private List<OrderDetailOutput> orderDetails;

	public OrderOutput() {
		super();
	}

	public OrderOutput(int id, String email, String fullname, String phone, String address, int status, int amount,
			Date createDate) {
		this.id = id;
		this.email = email;
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
		this.status = status;
		this.amount = amount;
		this.createDate = Utils.convertDateToString("yyyy-MM-dd HH:mm:ss", createDate);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAmount() {
		return Utils.currencyMoney(amount);
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatusName() {
		return Constant.PayEnum.getValueByCode(this.status);
	}

	public List<OrderDetailOutput> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailOutput> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
