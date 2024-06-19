package com.bit.healthpartnerboot.repository.jpa;

import com.bit.healthpartnerboot.entity.TodoFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoFoodRepository extends JpaRepository<TodoFood, Integer> {
    @Query(value = "select tf from TodoFood tf where tf.todo.seq = :todoSeq")
    List<TodoFood> findAllByTodo(Integer todoSeq);

    @Modifying
    @Query(value = "delete from TodoFood tf where tf.seq IN :seqList")
    void deleteBySeqList(List<Integer> seqList);

    @Modifying
    @Query(value = "delete from TodoFood tf where tf.todo.seq = :seq")
    void deleteByTodoSeq(Integer seq);
}
