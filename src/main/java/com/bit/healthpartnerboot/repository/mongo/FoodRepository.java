package com.bit.healthpartnerboot.repository.mongo;

import com.bit.healthpartnerboot.document.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FoodRepository extends MongoRepository<Food, String> {
    @Query(value = "{ '식품명': { $regex: ?0, $options: 'i' } }")
    List<Food> findAllByNameContaining(String name);

    @Query(value = "{ '_id': { $in: ?0 } }")
    List<Food> findAllByIdIn(List<String> ids);

    @Query(value = "{ '에너지(kcal)': { $lte: ?0 }, " +
            " $and: [" +
            " { $or: [ { '단백질(g)': { $eq: '' } }, { '단백질(g)': { $gte: ?1 } } ] }, " +
            " { $or: [ { '탄수화물(g)': { $eq: '' } }, { '탄수화물(g)': { $gte: ?2 } } ] }, " +
            " { $or: [ { '지방(g)': { $eq: '' } }, { '지방(g)': { $gte: ?3 } } ] }" +
            " ] }",
            sort = "{ '에너지(kcal)': 1, '단백질(g)': 1, '탄수화물(g)': 1, '지방(g)': 1 }")
    List<Food> findClosestFoods(String remainingCalories, String requiredProtein, String requiredCarbohydrates, String requiredFat, Pageable pageable);

}