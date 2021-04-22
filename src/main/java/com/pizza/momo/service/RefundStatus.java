package com.pizza.momo.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pizza.common.*;
import com.pizza.model.base.AbstractProcess;
import com.pizza.model.base.Environment;
import com.pizza.momo.model.HttpResponse;
import com.pizza.momo.model.RefundStatusRequest;
import com.pizza.momo.model.RefundStatusResponse;
import com.pizza.momo.utils.Encoder;

public class RefundStatus extends AbstractProcess {

	public RefundStatus(Environment environment) {
		super(environment);
	}

	public static List<RefundStatusResponse> process(Environment env, String requestId, String orderId) {
		List<RefundStatusResponse> response = null;

		try {
			RefundStatus refundStatus = new RefundStatus(env);

			RefundStatusRequest request = refundStatus.createRefundStatusRequest(requestId, orderId);
			response = refundStatus.execute(request);

		} catch (Exception e) {
			response = new ArrayList<>();
		}
		return response;
	}

	@SuppressWarnings("deprecation")
	public List<RefundStatusResponse> execute(RefundStatusRequest refundStatusRequest) {

		List<RefundStatusResponse> responseList = new ArrayList<>();

		try {
			String payload = getGson().toJson(refundStatusRequest, RefundStatusRequest.class);
			HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

			if (response.getStatus() == 200) {

				JsonParser jsonParser = new JsonParser();
				JsonArray jsonArray = (JsonArray) jsonParser.parse(response.getData());

				for (int i = 0; i < jsonArray.size(); i++) {
					JsonElement jsonElement = jsonArray.get(i);
					JsonElement obj = jsonElement.getAsJsonObject();

					RefundStatusResponse refundMoMoResponse = getGson().fromJson(obj, RefundStatusResponse.class);

					String rawData = Parameter.PARTNER_CODE + "=" + refundMoMoResponse.getPartnerCode() + "&"
							+ Parameter.ACCESS_KEY + "=" + refundMoMoResponse.getAccessKey() + "&"
							+ Parameter.REQUEST_ID + "=" + refundMoMoResponse.getRequestId() + "&" + Parameter.ORDER_ID
							+ "=" + refundMoMoResponse.getOrderId() + "&" + Parameter.ERROR_CODE + "="
							+ refundMoMoResponse.getErrorCode() + "&" + Parameter.TRANS_ID + "="
							+ refundMoMoResponse.getTransId() + "&" + Parameter.AMOUNT + "="
							+ refundMoMoResponse.getAmount() + "&" + Parameter.MESSAGE + "="
							+ refundMoMoResponse.getMessage() + "&" + Parameter.LOCAL_MESSAGE + "="
							+ refundMoMoResponse.getLocalMessage() + "&" + Parameter.REQUEST_TYPE + "="
							+ RequestType.QUERY_REFUND;

					String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

					if (signature.equals(refundMoMoResponse.getSignature())) {
						responseList.add(refundMoMoResponse);
					}
				}
			}
		} catch (Exception e) {
			responseList = new ArrayList<>();
		}

		return responseList;
	}

	public RefundStatusRequest createRefundStatusRequest(String requestId, String orderId) {
		String signature = "";

		try {
			String rawData = Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" + Parameter.ACCESS_KEY
					+ "=" + partnerInfo.getAccessKey() + "&" + Parameter.REQUEST_ID + "=" + requestId + "&"
					+ Parameter.ORDER_ID + "=" + orderId + "&" + Parameter.REQUEST_TYPE + "="
					+ RequestType.QUERY_REFUND;
			signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
		} catch (Exception e) {
		}

		return new RefundStatusRequest(partnerInfo.getPartnerCode(), orderId, partnerInfo.getAccessKey(), signature,
				requestId, RequestType.QUERY_REFUND);

	}
}
