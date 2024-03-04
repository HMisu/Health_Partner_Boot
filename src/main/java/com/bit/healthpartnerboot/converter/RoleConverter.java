package com.bit.healthpartnerboot.converter;

import com.bit.healthpartnerboot.dto.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.getLegacyCode();
    }

    @Override
    public Role convertToEntityAttribute(String legacyCode) {
        return Role.ofCode(legacyCode);
    }
}
