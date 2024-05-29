package com.bit.healthpartnerboot.repository.jpa;

import com.bit.healthpartnerboot.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "update Member m set m.name = :name, m.imgAddress= :img where m.email=:email")
    void updateByOAuth2Info(String email, String name, String img);

    @Modifying
    @Transactional
    @Query(value = "update Member m set m.password = :password where m.email=:email")
    void updatePasswordByEmail(String email, String password);


    @Modifying
    @Transactional
    @Query(value = "update Member m set m.name = :name, m.age = :age, m.height = :height, m.weight = :weight where m.email=:email")
    void updateByEmail(String email, String name, Integer age, Float height, Float weight);

    @Modifying
    @Transactional
    @Query(value = "update Member m set m.imgAddress= :img where m.email=:email")
    void updateImgByEmail(String email, String img);

    long countByEmail(String email);
}
