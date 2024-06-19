package com.bit.healthpartnerboot.repository.jpa;

import com.bit.healthpartnerboot.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CheckListRepository extends JpaRepository<CheckList, Integer> {
    @Modifying
    @Query(value = "delete from CheckList c where c.seq = :seq")
    void deleteBySeq(Integer seq);

    @Modifying
    @Query("update CheckList c set c.text = :text, c.isCheck = :isCheck where c.seq = :seq")
    void updateIsCheckAndText(Integer seq, String text, Boolean isCheck);
}
