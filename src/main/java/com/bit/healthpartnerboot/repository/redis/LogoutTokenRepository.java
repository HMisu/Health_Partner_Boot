package com.bit.healthpartnerboot.repository.redis;

import com.bit.healthpartnerboot.hash.LogoutToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutTokenRepository extends CrudRepository<LogoutToken, String> {
    LogoutToken findByAccessToken(String token);
}