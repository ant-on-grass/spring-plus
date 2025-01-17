package org.example.expert.domain.todo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u " +
        "WHERE (t.weather = :weather  OR :weather IS NULL) "+
        "AND (:select1_1 IS NULL OR t.modifiedAt >= :select1_1) " +
        "AND (:select1_2 IS NULL OR t.modifiedAt <= :select1_2) "+
        "ORDER BY t.modifiedAt DESC ")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable, @Param("weather") String weather, @Param("select1_1") LocalDateTime select1_1,
        @Param("select1_2") LocalDateTime  select1_2);

//    @Query("SELECT t FROM Todo t " +  // 정렬 연습용
//        "LEFT JOIN FETCH t.user u " +
//        "ORDER BY " +
//        "CASE WHEN :select1 = 'weather' THEN t.weather END DESC, " +
//        "CASE WHEN :select2 = 'updatedAt' THEN t.modifiedAt END DESC")
//    Page<Todo> findAllByWeatherAndSorting(@Param("select1") String select1,
//        @Param("select2") String select2, Pageable pageable);

    @Query("SELECT t FROM Todo t " +
        "LEFT JOIN t.user " +
        "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
