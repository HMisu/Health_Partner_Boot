package com.bit.healthpartnerboot.repository.redis;

import com.bit.healthpartnerboot.hash.EmailAuth;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuth, String> {
    EmailAuth findByEmail(String email);
}