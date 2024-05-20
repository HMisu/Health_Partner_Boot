package com.bit.healthpartnerboot.repository.redis;

import com.bit.healthpartnerboot.entity.LogoutToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutTokenRepository extends CrudRepository<LogoutToken, String> {
    LogoutToken findByAccessToken(String token);
}