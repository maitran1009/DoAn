package com.pizza.model.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pizza.momo.model.PartnerInfo;
import com.pizza.momo.utils.Execute;

public abstract class AbstractProcess {

	protected PartnerInfo partnerInfo;
	protected Environment environment;
	protected Execute execute = new Execute();

	public AbstractProcess(Environment environment) {
		this.environment = environment;
		this.partnerInfo = environment.getPartnerInfo();
	}

	public static Gson getGson() {
		return new GsonBuilder().disableHtmlEscaping().create();
	}
}
