package com.pizza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pizza.model.entity.Province;
import com.pizza.model.output.CityOutput;
import com.pizza.model.output.DistrictOutput;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {
	@Query("SELECT DISTINCT new com.pizza.model.output.CityOutput(cityId, cityName) FROM Province")
	List<CityOutput> findDistinctCity();

	@Query("SELECT DISTINCT new com.pizza.model.output.DistrictOutput(districtId, districtName, cityId) FROM Province Where cityId = :cityId")
	List<DistrictOutput> findDistinctDistrict(@Param("cityId") int cityId);

	List<Province> findByCityIdAndDistrictId(int cityId, int districtId);
}
