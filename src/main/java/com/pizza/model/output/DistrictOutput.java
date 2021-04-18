package com.pizza.model.output;

public class DistrictOutput {
	private int districtId;
	private String districtName;
	private int cityId;

	public DistrictOutput() {
		super();
	}

	public DistrictOutput(int districtId, String districtName, int cityId) {
		super();
		this.districtId = districtId;
		this.districtName = districtName;
		this.cityId = cityId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}
