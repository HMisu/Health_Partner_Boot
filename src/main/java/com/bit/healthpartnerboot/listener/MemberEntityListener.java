package com.bit.healthpartnerboot.listener;

import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.MemberHistory;
import com.bit.healthpartnerboot.repository.jpa.MemberHistoryRepository;
import com.bit.healthpartnerboot.support.BeanUtils;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

public class MemberEntityListener {

    @PostLoad
    public void postLoad(Member member) {
        member.setOriginalHeight(member.getHeight());
        member.setOriginalWeight(member.getWeight());
    }

    @PostPersist
    @PostUpdate
    public void prePersistAndPreUpdate(Member member) {
        MemberHistoryRepository userHistoryRepository = BeanUtils.getBean(MemberHistoryRepository.class);

        if (isMemberChanged(member)) {
            MemberHistory userHistory = MemberHistory.builder()
                    .height(member.getHeight())
                    .weight(member.getWeight())
                    .member(member)
                    .build();

            userHistoryRepository.save(userHistory);
        }
    }

    private boolean isMemberChanged(Member member) {
        return !member.getHeight().equals(member.getOriginalHeight()) ||
                !member.getWeight().equals(member.getOriginalWeight());
    }
}