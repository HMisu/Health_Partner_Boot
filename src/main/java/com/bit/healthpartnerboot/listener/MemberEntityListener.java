package com.bit.healthpartnerboot.listener;

import com.bit.healthpartnerboot.entity.Member;
import com.bit.healthpartnerboot.entity.MemberHistory;
import com.bit.healthpartnerboot.repository.jpa.MemberHistoryRepository;
import com.bit.healthpartnerboot.support.BeanUtils;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class MemberEntityListener {

    @PostLoad
    public void postLoad(Member member) {
        member.setOriginalHeight(member.getHeight());
        member.setOriginalWeight(member.getWeight());
    }

    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Member member) {
        MemberHistoryRepository userHistoryRepository = BeanUtils.getBean(MemberHistoryRepository.class);

        calculateAndSetBMI(member);

        if (isMemberChanged(member)) {
            MemberHistory userHistory = MemberHistory.builder()
                    .height(member.getHeight())
                    .weight(member.getWeight())
                    .bmi(member.getBmi())
                    .member(member)
                    .build();

            userHistoryRepository.save(userHistory);
        }
    }

    private boolean isMemberChanged(Member member) {
        return member.getHeight() != member.getOriginalHeight() ||
                member.getWeight() != member.getOriginalWeight();
    }

    private void calculateAndSetBMI(Member member) {
        float heightInMeters = member.getHeight() / 100;
        if (heightInMeters > 0) {
            float bmi = member.getWeight() / (heightInMeters * heightInMeters);
            member.setBmi(bmi);
        }
    }
}