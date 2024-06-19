package com.bit.healthpartnerboot.repository.jpa;

import com.bit.healthpartnerboot.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Todo findBySeq(long seq);

    @Query("select t from Todo t where YEAR(t.date) = :year and MONTH(t.date) = :month and DAY(t.date) = :day and t.member.email = :writer")
    List<Todo> findAllByDate(int year, int month, int day, String writer);

    @Query("select t.seq from Todo t where YEAR(t.date) = :year and MONTH(t.date) = :month and DAY(t.date) = :day and t.member.email = :writer")
    List<Integer> findSeqByDate(int year, int month, int day, String writer);

    @Query("SELECT t FROM Todo t WHERE t.date >= :weekStart AND t.date <= :weekEnd AND t.member.email = :writer")
    List<Todo> findAllByWeek(LocalDateTime weekStart, LocalDateTime weekEnd, String writer);

    @Modifying
    @Query(value = "update Todo t set t.title = :title, t.diary = :diary, t.date = :date where t.seq = :seq")
    void updateTodo(Integer seq, String title, String diary, LocalDateTime date);

    @Modifying
    @Query(value = "delete from Todo t where t.seq = :seq")
    void deleteBySeq(Integer seq);
}
