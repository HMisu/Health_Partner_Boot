package com.bit.healthpartnerboot.converter;

import com.bit.healthpartnerboot.dto.AuthType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class AuthTypeConverter implements AttributeConverter<AuthType, String> {
    @Override
    public String convertToDatabaseColumn(AuthType authType) {
        return authType.getLegacyCode();
    }

    @Override
    public AuthType convertToEntityAttribute(String legacyCode) {
        return AuthType.ofCode(legacyCode);
    }
}
