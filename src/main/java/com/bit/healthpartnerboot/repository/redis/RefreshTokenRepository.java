package com.bit.healthpartnerboot.repository.redis;

import com.bit.healthpartnerboot.hash.RefreshToken;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

@CacheConfig(cacheNames = "refreshTokens")
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    @Cacheable(key = "#memberEmail")
    RefreshToken findByMemberEmail(String memberEmail);

    @CacheEvict(key = "#memberEmail")
    void deleteByMemberEmail(String memberEmail);
}