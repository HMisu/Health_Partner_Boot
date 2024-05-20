package com.bit.healthpartnerboot.repository.redis;

import com.bit.healthpartnerboot.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByMemberEmail(String memberEmail);
    
    void deleteByMemberEmail(String memberEmail);
}