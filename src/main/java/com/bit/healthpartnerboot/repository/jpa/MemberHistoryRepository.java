package com.bit.healthpartnerboot.repository.jpa;

import com.bit.healthpartnerboot.entity.MemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory, Integer> {
    @Query(value = "select mh from MemberHistory mh where mh.member.email = :email")
    List<MemberHistory> findAllByMember(String email);
}
