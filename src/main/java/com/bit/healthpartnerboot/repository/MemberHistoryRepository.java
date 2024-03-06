package com.bit.healthpartnerboot.repository;

import com.bit.healthpartnerboot.entity.MemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory, Long> {
    List<MemberHistory> findByMember_Email(String email);
}
