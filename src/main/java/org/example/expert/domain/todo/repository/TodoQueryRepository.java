package org.example.expert.domain.todo.repository;


import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.request.TestRequestDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoQueryRepository {

  private final JPAQueryFactory queryFactory;

  public Todo findByIdWithUser(Long todoId) {
    return queryFactory
        .selectFrom(todo)
        .leftJoin(todo.user,user)
        .fetchJoin()
// 1.    .join(todo.user,user) // inner 가 더 빠르다.
// 1.       .fetchJoin()
//        .on(todo.user.id.eq(user.id)) // < - 틀린 방법 xx
//        .fetchJoin() // < - 틀린 방법 xx
        .where(todo.id.eq(todoId))
        .fetchOne();
  }

  public List<Todo> testdsl(TestRequestDto dto) {
    return queryFactory.selectFrom(todo).where(likeTitle(dto.getTitle())).fetch();
  }

  private BooleanExpression likeTitle(String title) {
    if(title == null) {
      return null;
    }
    return todo.title.like(title+'%');
  }

}