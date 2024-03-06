package com.bit.healthpartnerboot.converter;

import com.bit.healthpartnerboot.dto.MealType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class MealTypeConverter implements AttributeConverter<MealType, String> {
    @Override
    public String convertToDatabaseColumn(MealType mealType) {
        return mealType.getLegacyCode();
    }

    @Override
    public MealType convertToEntityAttribute(String legacyCode) {
        return MealType.ofCode(legacyCode);
    }
}
