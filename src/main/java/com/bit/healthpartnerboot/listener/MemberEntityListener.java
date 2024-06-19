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
        if (member.getHeight() != null) {
            member.setOriginalHeight(member.getHeight().floatValue());
        }
        if (member.getWeight() != null) {
            member.setOriginalWeight(member.getWeight().floatValue());
        }
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
        return (member.getHeight() != null && !member.getHeight().equals(member.getOriginalHeight())) ||
                (member.getWeight() != null && !member.getWeight().equals(member.getOriginalWeight()));
    }

    private void calculateAndSetBMI(Member member) {
        Float height = member.getHeight();
        Float weight = member.getWeight();

        if (height != null && weight != null) {
            float heightInMeters = height / 100;
            if (heightInMeters > 0) {
                float bmi = weight / (heightInMeters * heightInMeters);
                member.setBmi(bmi);
            }
        }
    }
}
