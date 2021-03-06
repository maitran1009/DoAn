package com.pizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizza.model.entity.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

}
