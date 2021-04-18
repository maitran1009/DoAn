package com.pizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pizza.model.entity.Province;
import com.pizza.model.output.CityOutput;
import com.pizza.model.output.DistrictOutput;
import com.pizza.service.ProvinceService;

@RestController
@RequestMapping("/province")
public class ProvinceController {
	@Autowired
	private ProvinceService provinceService;

	@GetMapping("city")
	public List<CityOutput> getListCity() {
		return provinceService.getListCity();
	}

	@GetMapping("district")
	public List<DistrictOutput> getListDistrictByCity(@RequestParam Integer cityId) {
		return provinceService.getListDistrictByCity(cityId);
	}

	@GetMapping("ward")
	public List<Province> getListWardByDistrict(@RequestParam Integer cityId, @RequestParam Integer districtId) {
		return provinceService.getListWardByDistrict(cityId, districtId);
	}
}
