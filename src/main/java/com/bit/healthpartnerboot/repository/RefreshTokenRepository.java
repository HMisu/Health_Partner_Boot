package com.bit.healthpartnerboot.repository;

import com.bit.healthpartnerboot.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}