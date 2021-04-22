package com.pizza.momo.service;

import com.pizza.common.*;
import com.pizza.model.base.AbstractProcess;
import com.pizza.model.base.Environment;
import com.pizza.momo.model.HttpResponse;
import com.pizza.momo.model.RefundMoMoRequest;
import com.pizza.momo.model.RefundMoMoResponse;
import com.pizza.momo.utils.Encoder;

public class RefundMoMo extends AbstractProcess {

	public RefundMoMo(Environment environment) {
		super(environment);
	}

	public static RefundMoMoResponse process(Environment env, String requestId, String orderId, String amount,
			String transId) throws Exception {
		try {
			RefundMoMo refundMoMo = new RefundMoMo(env);

			RefundMoMoRequest request = refundMoMo.createRefundRequest(requestId, orderId, amount, transId);
			RefundMoMoResponse response = refundMoMo.execute(request);
			return response;
		} catch (Exception exception) {

		}
		return null;
	}

	public RefundMoMoResponse execute(RefundMoMoRequest request) {
		try {
			String payload = getGson().toJson(request, RefundMoMoRequest.class);

			HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

			if (response.getStatus() != 200) {

			}

			RefundMoMoResponse refundMoMoResponse = getGson().fromJson(response.getData(), RefundMoMoResponse.class);

			String rawData = Parameter.PARTNER_CODE + "=" + refundMoMoResponse.getPartnerCode() + "&"
					+ Parameter.ACCESS_KEY + "=" + refundMoMoResponse.getAccessKey() + "&" + Parameter.REQUEST_ID + "="
					+ refundMoMoResponse.getRequestId() + "&" + Parameter.ORDER_ID + "="
					+ refundMoMoResponse.getOrderId() + "&" + Parameter.ERROR_CODE + "="
					+ refundMoMoResponse.getErrorCode() + "&" + Parameter.TRANS_ID + "="
					+ refundMoMoResponse.getTransId() + "&" + Parameter.MESSAGE + "=" + refundMoMoResponse.getMessage()
					+ "&" + Parameter.LOCAL_MESSAGE + "=" + refundMoMoResponse.getLocalMessage() + "&"
					+ Parameter.REQUEST_TYPE + "=" + RequestType.REFUND_MOMO_WALLET;

			String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

			if (signature.equals(refundMoMoResponse.getSignature())) {
				return refundMoMoResponse;
			} else {
			}
		} catch (Exception e) {
		}
		return null;
	}

	public RefundMoMoRequest createRefundRequest(String requestId, String orderId, String amount, String transId) {
		String signature = "";

		try {
			String rawData = Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" + Parameter.ACCESS_KEY
					+ "=" + partnerInfo.getAccessKey() + "&" + Parameter.REQUEST_ID + "=" + requestId + "&"
					+ Parameter.AMOUNT + "=" + amount + "&" + Parameter.ORDER_ID + "=" + orderId + "&"
					+ Parameter.TRANS_ID + "=" + transId + "&" + Parameter.REQUEST_TYPE + "="
					+ RequestType.REFUND_MOMO_WALLET;
			signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

		} catch (Exception e) {

		}

		RefundMoMoRequest request = new RefundMoMoRequest(partnerInfo.getPartnerCode(), orderId,
				partnerInfo.getAccessKey(), amount, signature, requestId, RequestType.REFUND_MOMO_WALLET, transId);
		return request;
	}
}
