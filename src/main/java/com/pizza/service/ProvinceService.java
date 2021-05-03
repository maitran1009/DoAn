 package com.pizza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza.model.entity.Province;
import com.pizza.model.output.CityOutput;
import com.pizza.model.output.DistrictOutput;
import com.pizza.repository.ProvinceRepository;

@Service
public class ProvinceService {
	@Autowired
	private ProvinceRepository provinceRepository;

	public List<CityOutput> getListCity() {
		return provinceRepository.findDistinctCity();
	}

	public List<DistrictOutput> getListDistrictByCity(int cityId) {
		return provinceRepository.findDistinctDistrict(cityId);
	}

	public List<Province> getListWardByDistrict(int cityId, int districtId) {
		return provinceRepository.findByCityIdAndDistrictId(cityId, districtId);
	}
}
