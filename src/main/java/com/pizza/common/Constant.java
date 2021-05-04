package com.pizza.common;

public class Constant {
	public static final int STATUS_ENABLE = 1;
	public static final int STATUS_DISABLE = 0;
	public static final String AMOUNT = "amount";
	public static final String SESSION_CART = "carts";
	public static final String SESSION_PAY_INPUT = "payInput";
	public static final String SESSION_CODE_CONFIRM = "codeConfirm";
	public static final String RETURN_URL = "http://localhost:9090/mySuFood/pay/pay-success";
	public static final String NOTIFY_URL = "http://localhost:9090/mySuFood/pay/pay-success";

	public enum PayEnum {
		PAY_FAIL(0, "Đã hủy"), PAY_DEFAULT(1, "Đang giao hàng"), PAY_SUCCESS(2, "Đã nhận hàng");

		private int code;
		private String value;

		private PayEnum(int code, String value) {
			this.code = code;
			this.value = value;
		}

		public static String getValueByCode(int code) {
			for (PayEnum payEnum : PayEnum.values()) {
				if (payEnum.getCode() == code)
					return payEnum.getValue();
			}
			return null;
		}

		public int getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}
	}

	public enum PaymentEnum {
		PAYMENT_IN_PLACE(1, "Thu tiền tận nơi"), 
		PAYMENT_VNPAY(2, "Thanh toán VNPAY"),
		PAYMENT_MOMO(3, "Thanh toán Momo");

		private int code;
		private String value;

		private PaymentEnum(int code, String value) {
			this.code = code;
			this.value = value;
		}

		public static String getValueByCode(int code) {
			for (PaymentEnum payment : PaymentEnum.values()) {
				if (payment.getCode() == code)
					return payment.getValue();
			}
			return null;
		}

		public int getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}
	}
}
