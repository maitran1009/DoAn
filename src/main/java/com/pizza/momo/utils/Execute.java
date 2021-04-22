package com.pizza.momo.utils;

import java.io.IOException;

import com.pizza.model.base.BaseObject;
import com.pizza.momo.model.HttpRequest;
import com.pizza.momo.model.HttpResponse;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class Execute extends BaseObject {

	OkHttpClient client = new OkHttpClient();

	public HttpResponse sendToMoMo(String endpoint, String payload) {
		logger.info(">>>>>sendToMoMo Start >>>>");
		HttpResponse response;
		try {

			HttpRequest httpRequest = new HttpRequest("POST", endpoint, payload, "application/json");

			Request request = createRequest(httpRequest);

			logger.info("sendToMoMo request: {}", request);

			Response result = client.newCall(request).execute();

			response = new HttpResponse(result.code(), result.body().string(), result.headers());

		} catch (Exception e) {
			logger.error("sendToMoMo has exception: ", e);
			response = null;
		}

		logger.info("sendToMoMo response: {}", response);
		logger.info(">>>>>sendToMoMo End >>>>");
		return response;
	}

	public static Request createRequest(HttpRequest request) {
		RequestBody body = RequestBody.create(MediaType.get(request.getContentType()), request.getPayload());
		return new Request.Builder().method(request.getMethod(), body).url(request.getEndpoint()).build();
	}

	public String getBodyAsString(Request request) throws IOException {
		Buffer buffer = new Buffer();
		RequestBody body = request.body();
		body.writeTo(buffer);
		return buffer.readUtf8();
	}
}
