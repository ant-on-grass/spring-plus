package org.example.expert.domain.todo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.expert.domain.todo.entity.Todo;

@Getter
@AllArgsConstructor
public class TestResponseDto {

  private String title;

  private String content;

  public static TestResponseDto testCreate(Todo todo) {
    return new TestResponseDto(
        todo.getTitle(),
        todo.getContents()
    );
  }
}
