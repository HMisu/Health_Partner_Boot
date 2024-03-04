package com.bit.healthpartnerboot.service.impl;

import com.bit.healthpartnerboot.api.MenuzenApiExplorer;
import com.bit.healthpartnerboot.entity.Food;
import com.bit.healthpartnerboot.repository.FoodRepository;
import com.bit.healthpartnerboot.service.FoodService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodServiceImpl implements FoodService {
    private final MenuzenApiExplorer menuzenApiExplorer;
    private final FoodRepository foodRepository;

    @Override
    public void saveToDatabase() {
        for (int i = 1; i <= 101; i++) {
            String json = menuzenApiExplorer.getFood(String.valueOf(i));

            List<Food> foods = new ArrayList<>();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(json);
                JsonNode listNode = rootNode.path("response").path("list");

                for (JsonNode itemNode : listNode) {
                    Food food = Food.builder()
                            .code(itemNode.get("fd_Code").asText())
                            .upperFdGrupp(itemNode.get("upper_Fd_Grupp_Nm").asText())
                            .fdGrupp(itemNode.get("fd_Grupp_Nm").asText())
                            .name(itemNode.get("fd_Nm").asText())
                            .weight(Float.parseFloat(itemNode.get("fd_Wgh").asText()))
                            .foodCnt(Integer.parseInt(itemNode.get("food_Cnt").asText()))
                            .imgAddress(itemNode.get("image_Address").asText())
                            .build();

                    foods.add(food);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error parsing JSON to Food list", e);
            }

            foodRepository.saveAllAndFlush(foods);
        }
    }
}
