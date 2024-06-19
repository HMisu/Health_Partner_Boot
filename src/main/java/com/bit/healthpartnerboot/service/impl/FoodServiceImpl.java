package com.bit.healthpartnerboot.service.impl;

import com.bit.healthpartnerboot.document.Food;
import com.bit.healthpartnerboot.dto.FoodDTO;
import com.bit.healthpartnerboot.dto.RecommendFoodDTO;
import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.TodoFood;
import com.bit.healthpartnerboot.repository.mongo.FoodRepository;
import com.bit.healthpartnerboot.service.FoodService;
import com.bit.healthpartnerboot.service.MemberService;
import com.bit.healthpartnerboot.service.NutritionCalculator;
import com.bit.healthpartnerboot.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final TodoService todoService;
    private final MemberService memberService;

    @Override
    public FoodDTO getTodoFood(String foodCode) {
        return foodRepository.findById(foodCode).get().toDTO();
    }

    @Override
    public List<FoodDTO> searchFood(String keyword) {
        return foodRepository.findAllByNameContaining(keyword).stream()
                .map(Food::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecommendFoodDTO getRecommendedFoods(Member member) {
        double[] currentMacros = calculateCurrentMacros(member);

        double bmr = NutritionCalculator.calculateBMR(member.getWeight(), member.getHeight(), member.getAge(), member.getGender());
        double tdee = NutritionCalculator.calculateTDEE(bmr, member.getActivityLevel());
        double[] targetMacros = NutritionCalculator.calculateMacros(tdee);

        double remainingCalories = tdee - currentMacros[0];
        double remainingProtein = targetMacros[0] - currentMacros[1];
        double remainingCarbohydrates = targetMacros[1] - currentMacros[2];
        double remainingFat = targetMacros[2] - currentMacros[3];

        RecommendFoodDTO responseDTO = new RecommendFoodDTO();
        responseDTO.setRemainingCalories(remainingCalories);
        responseDTO.setRemainingProtein(remainingProtein);
        responseDTO.setRemainingCarbohydrates(remainingCarbohydrates);
        responseDTO.setRemainingFat(remainingFat);

        if (remainingCalories > 0) {
            List<Food> recommendedFoods = findRecommendedFoods(remainingCalories, remainingProtein, remainingCarbohydrates, remainingFat);
            responseDTO.setFoodList(recommendedFoods.stream().map(Food::toDTO).collect(Collectors.toList()));
        }

        return responseDTO;
    }

    private double[] calculateCurrentMacros(Member member) {
        List<Integer> writtenTodoList = todoService.getSeqByDate(LocalDate.now(), member.getEmail());
        if (writtenTodoList.isEmpty()) {
            return new double[]{0, 0, 0, 0};
        }

        List<TodoFood> todoFoodList = todoService.getTodoFoodList(writtenTodoList);
        if (todoFoodList.isEmpty()) {
            return new double[]{0, 0, 0, 0};
        }

        List<String> foodCodeList = todoFoodList.stream().map(TodoFood::getFoodCode).collect(Collectors.toList());
        List<Food> foodList = foodRepository.findAllByIdIn(foodCodeList);

        double currentCalorie = 0;
        double currentProtein = 0;
        double currentCarbohydrate = 0;
        double currentFat = 0;

        for (Food food : foodList) {
            currentCalorie += parseDoubleOrZero(food.getEnergy());
            currentProtein += parseDoubleOrZero(food.getProtein());
            currentCarbohydrate += parseDoubleOrZero(food.getCarbohydrates());
            currentFat += parseDoubleOrZero(food.getFat());
        }

        return new double[]{currentCalorie, currentProtein, currentCarbohydrate, currentFat};
    }

    private double parseDoubleOrZero(String value) {
        return (value != null && !value.isEmpty()) ? Double.parseDouble(value) : 0;
    }

    private List<Food> findRecommendedFoods(double remainingCalories, double remainingProtein, double remainingCarbohydrates, double remainingFat) {
        Pageable pageable = PageRequest.of(0, 200);
        List<Food> foods = foodRepository.findClosestFoods(
                String.valueOf(remainingCalories),
                String.valueOf(remainingProtein),
                String.valueOf(remainingCarbohydrates),
                String.valueOf(remainingFat),
                pageable
        );
        Collections.shuffle(foods);
        return foods.size() > 4 ? foods.subList(0, 4) : foods;
    }
}
