package com.bit.healthpartnerboot.repository;

import com.bit.healthpartnerboot.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

}
