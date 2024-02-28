package com.bit.healthpartnerboot.listener;


import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.MemberHistory;
import com.bit.healthpartnerboot.repository.MemberHistoryRepository;
import com.bit.healthpartnerboot.support.BeanUtils;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

public class MemberEntityListener {
    @PostPersist
    @PostUpdate
    public void prePersistAndPreUpdate(Object o) {
        MemberHistoryRepository userHistoryRepository = BeanUtils.getBean(MemberHistoryRepository.class);

        Member member = (Member) o;

        MemberHistory userHistory = new MemberHistory();
        userHistory.setName(member.getUsername());
        userHistory.setEmail(member.getEmail());
        userHistory.setUser(member);
        userHistoryRepository.save(userHistory);
    }
}
