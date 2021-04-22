package com.pizza.momo.service;

import com.pizza.common.*;
import com.pizza.model.base.AbstractProcess;
import com.pizza.model.base.Environment;
import com.pizza.momo.model.HttpResponse;
import com.pizza.momo.model.QueryStatusTransactionRequest;
import com.pizza.momo.model.QueryStatusTransactionResponse;
import com.pizza.momo.utils.Encoder;

public class QueryStatusTransaction extends AbstractProcess {

	public QueryStatusTransaction(Environment environment) {
		super(environment);
	}

	/**
	 * Check Query Transaction Status on Payment Gateway
	 *
	 * @param orderId   MoMo's order ID
	 * @param requestId request ID
	 **/
	public static QueryStatusTransactionResponse process(Environment env, String orderId, String requestId) {
		try {
			QueryStatusTransaction queryStatusTransaction = new QueryStatusTransaction(env);

			QueryStatusTransactionRequest queryStatusRequest = queryStatusTransaction.createQueryRequest(orderId,
					requestId);
			QueryStatusTransactionResponse queryStatusResponse = queryStatusTransaction.execute(queryStatusRequest);

			return queryStatusResponse;
		} catch (Exception e) {
		}
		return null;
	}

	public QueryStatusTransactionResponse execute(QueryStatusTransactionRequest request) {

		try {
			String payload = getGson().toJson(request, QueryStatusTransactionRequest.class);

			HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

			QueryStatusTransactionResponse queryStatusResponse = getGson().fromJson(response.getData(),
					QueryStatusTransactionResponse.class);

			String rawData = Parameter.PARTNER_CODE + "=" + queryStatusResponse.getPartnerCode() + "&"
					+ Parameter.ACCESS_KEY + "=" + queryStatusResponse.getAccessKey() + "&" + Parameter.REQUEST_ID + "="
					+ queryStatusResponse.getRequestId() + "&" + Parameter.ORDER_ID + "="
					+ queryStatusResponse.getOrderId() + "&" + Parameter.ERROR_CODE + "="
					+ queryStatusResponse.getErrorCode() + "&" + Parameter.TRANS_ID + "="
					+ queryStatusResponse.getTransId() + "&" + Parameter.AMOUNT + "=" + queryStatusResponse.getAmount()
					+ "&" + Parameter.MESSAGE + "=" + queryStatusResponse.getMessage() + "&" + Parameter.LOCAL_MESSAGE
					+ "=" + queryStatusResponse.getLocalMessage() + "&" + Parameter.REQUEST_TYPE + "="
					+ RequestType.TRANSACTION_STATUS + "&" + Parameter.PAY_TYPE + "=" + queryStatusResponse.getPayType()
					+ "&" + Parameter.EXTRA_DATA + "=" + queryStatusResponse.getExtraData();

			String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

			if (signature.equals(queryStatusResponse.getSignature())) {
				return queryStatusResponse;
			} else {
			}

		} catch (Exception e) {
		}
		return null;

	}

	public QueryStatusTransactionRequest createQueryRequest(String orderId, String requestId) {
		String signature = "";
		try {
			String rawData = Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" + Parameter.ACCESS_KEY
					+ "=" + partnerInfo.getAccessKey() + "&" + Parameter.REQUEST_ID + "=" + requestId + "&"
					+ Parameter.ORDER_ID + "=" + orderId + "&" + Parameter.REQUEST_TYPE + "="
					+ RequestType.TRANSACTION_STATUS;
			signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
		} catch (Exception e) {
		}

		QueryStatusTransactionRequest request = new QueryStatusTransactionRequest(partnerInfo.getPartnerCode(), orderId,
				partnerInfo.getAccessKey(), signature, requestId, RequestType.TRANSACTION_STATUS);

		return request;
	}

}
