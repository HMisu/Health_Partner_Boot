package com.bit.healthpartnerboot.repository;

import com.bit.healthpartnerboot.jwt.UserRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface UserRefreshTokenRepository extends CrudRepository<UserRefreshToken, String> {
}