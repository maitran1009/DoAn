package com.pizza.momo.service;

import com.pizza.common.*;
import com.pizza.model.base.AbstractProcess;
import com.pizza.model.base.Environment;
import com.pizza.momo.model.CaptureMoMoRequest;
import com.pizza.momo.model.CaptureMoMoResponse;
import com.pizza.momo.model.HttpResponse;
import com.pizza.momo.model.PaymentResponse;
import com.pizza.momo.utils.Encoder;

public class CaptureMoMo extends AbstractProcess {

	public CaptureMoMo(Environment environment) {
		super(environment);
	}

	/**
	 * Capture MoMo Process on Payment Gateway
	 *
	 * @param amount
	 * @param extraData
	 * @param orderInfo
	 * @param env       name of the environment (dev or prod)
	 * @param orderId   unique order ID in MoMo system
	 * @param requestId request ID
	 * @param returnURL URL to redirect customer
	 * @param notifyURL URL for MoMo to return transaction status to merchant
	 * @return CaptureMoMoResponse
	 **/

	public static CaptureMoMoResponse process(Environment env, String orderId, String requestId, String amount,
			String orderInfo, String returnURL, String notifyURL, String extraData) {
		CaptureMoMoResponse response = new CaptureMoMoResponse();

		try {
			CaptureMoMo m2Processor = new CaptureMoMo(env);

			CaptureMoMoRequest captureMoMoRequest = m2Processor.createPaymentCreationRequest(orderId, requestId, amount,
					orderInfo, returnURL, notifyURL, extraData);
			response = m2Processor.execute(captureMoMoRequest);
		} catch (Exception exception) {
			response = null;
		}
		return response;
	}

	public CaptureMoMoResponse execute(CaptureMoMoRequest request) {
		try {

			String payload = getGson().toJson(request, CaptureMoMoRequest.class);
			StringBuilder rawData = new StringBuilder();

			HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

			CaptureMoMoResponse captureMoMoResponse = getGson().fromJson(response.getData(), CaptureMoMoResponse.class);

			rawData.append(Parameter.REQUEST_ID);
			rawData.append("=");
			rawData.append(captureMoMoResponse.getRequestId());
			rawData.append("&");
			rawData.append(Parameter.ORDER_ID);
			rawData.append("=");
			rawData.append(captureMoMoResponse.getOrderId());
			rawData.append("&");
			rawData.append(Parameter.MESSAGE);
			rawData.append("=");
			rawData.append(captureMoMoResponse.getMessage());
			rawData.append("&");
			rawData.append(Parameter.LOCAL_MESSAGE);
			rawData.append("=");
			rawData.append(captureMoMoResponse.getLocalMessage());
			rawData.append("&");
			rawData.append(Parameter.PAY_URL);
			rawData.append("=");
			rawData.append(captureMoMoResponse.getPayUrl());
			rawData.append("&");
			rawData.append(Parameter.ERROR_CODE);
			rawData.append("=");
			rawData.append(captureMoMoResponse.getErrorCode());
			rawData.append("&");
			rawData.append(Parameter.REQUEST_TYPE);
			rawData.append("=");
			rawData.append(RequestType.CAPTURE_MOMO_WALLET);

			String signResponse = Encoder.signHmacSHA256(rawData.toString(), partnerInfo.getSecretKey());

			if (signResponse.equals(captureMoMoResponse.getSignature())) {
				return captureMoMoResponse;
			} else {
				throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
			}

		} catch (Exception exception) {
			throw new IllegalArgumentException("Invalid params capture MoMo Request");
		}
	}

	/**
	 * @param orderId
	 * @param requestId
	 * @param amount
	 * @param orderInfo
	 * @param returnUrl
	 * @param notifyUrl
	 * @param extraData
	 * @return
	 */
	public CaptureMoMoRequest createPaymentCreationRequest(String orderId, String requestId, String amount,
			String orderInfo, String returnUrl, String notifyUrl, String extraData) {

		try {
			StringBuilder requestRawData = new StringBuilder();

			requestRawData.append(Parameter.PARTNER_CODE);
			requestRawData.append("=").append(partnerInfo.getPartnerCode());
			requestRawData.append("&");
			requestRawData.append(Parameter.ACCESS_KEY);
			requestRawData.append("=").append(partnerInfo.getAccessKey());
			requestRawData.append("&");
			requestRawData.append(Parameter.REQUEST_ID);
			requestRawData.append("=").append(requestId);
			requestRawData.append("&");
			requestRawData.append(Parameter.AMOUNT);
			requestRawData.append("=");
			requestRawData.append(amount);
			requestRawData.append("&").append(Parameter.ORDER_ID);
			requestRawData.append("=");
			requestRawData.append(orderId);
			requestRawData.append("&");
			requestRawData.append(Parameter.ORDER_INFO).append("=");
			requestRawData.append(orderInfo);
			requestRawData.append("&");
			requestRawData.append(Parameter.RETURN_URL);
			requestRawData.append("=").append(returnUrl);
			requestRawData.append("&");
			requestRawData.append(Parameter.NOTIFY_URL);
			requestRawData.append("=");
			requestRawData.append(notifyUrl).append("&");
			requestRawData.append(Parameter.EXTRA_DATA);
			requestRawData.append("=");
			requestRawData.append(extraData);

			String signRequest = Encoder.signHmacSHA256(requestRawData.toString(), partnerInfo.getSecretKey());

			return new CaptureMoMoRequest(partnerInfo.getPartnerCode(), orderId, orderInfo, partnerInfo.getAccessKey(),
					amount, signRequest, extraData, requestId, notifyUrl, returnUrl, RequestType.CAPTURE_MOMO_WALLET);
		} catch (Exception e) {
			return null;
		}
	}

	public PaymentResponse resultCaptureMoMoWallet(PaymentResponse paymentResponse) {
		StringBuilder rawData = new StringBuilder();

		rawData.append(Parameter.PARTNER_CODE);
		rawData.append("=");
		rawData.append(paymentResponse.getPartnerCode());
		rawData.append("&");
		rawData.append(Parameter.ACCESS_KEY);
		rawData.append("=");
		rawData.append(paymentResponse.getAccessKey());
		rawData.append("&");
		rawData.append(Parameter.REQUEST_ID);
		rawData.append("=");
		rawData.append(paymentResponse.getRequestId());
		rawData.append("&");
		rawData.append(Parameter.AMOUNT);
		rawData.append("=");
		rawData.append(paymentResponse.getAmount());
		rawData.append("&");
		rawData.append(Parameter.ORDER_ID);
		rawData.append("=");
		rawData.append(paymentResponse.getOrderId());
		rawData.append("&");
		rawData.append(Parameter.ORDER_INFO);
		rawData.append("=");
		rawData.append(paymentResponse.getOrderInfo());
		rawData.append("&");
		rawData.append(Parameter.ORDER_TYPE);
		rawData.append("=");
		rawData.append(paymentResponse.getOrderType());
		rawData.append("&");
		rawData.append(Parameter.TRANS_ID);
		rawData.append("=");
		rawData.append(paymentResponse.getTransId());
		rawData.append("&");
		rawData.append(Parameter.MESSAGE);
		rawData.append("=");
		rawData.append(paymentResponse.getMessage());
		rawData.append("&");
		rawData.append(Parameter.LOCAL_MESSAGE);
		rawData.append("=");
		rawData.append(paymentResponse.getLocalMessage());
		rawData.append("&");
		rawData.append(Parameter.RESPONSE_TIME);
		rawData.append("=");
		rawData.append(paymentResponse.getResponseDate());
		rawData.append("&");
		rawData.append(Parameter.ERROR_CODE);
		rawData.append("=");
		rawData.append(paymentResponse.getErrorCode());
		rawData.append("&");
		rawData.append(Parameter.PAY_TYPE);
		rawData.append("=");
		rawData.append(paymentResponse.getPayType());
		rawData.append("&");
		rawData.append(Parameter.EXTRA_DATA);
		rawData.append("=");
		rawData.append(paymentResponse.getExtraData());

		String signature;
		try {
			signature = Encoder.signHmacSHA256(rawData.toString(), partnerInfo.getSecretKey());
			if (!signature.equals(paymentResponse.getSignature())) {
				throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymentResponse;
	}

}
