package org.example.expert.domain.todo.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.TestRequestDto;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TestResponseDto;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.service.TodoService;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<TodoSaveResponse> saveTodo(
            @Auth AuthUser authUser,
            @Valid @RequestBody TodoSaveRequest todoSaveRequest
    ) {
        return ResponseEntity.ok(todoService.saveTodo(authUser, todoSaveRequest));
    }

    @GetMapping("/todos") // 검색 조건 연습용
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String weather,
        @RequestParam(required = false) String select1_1,
        @RequestParam(required = false) String select1_2
    ) {
        Sort sort = Sort.unsorted();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

      return ResponseEntity.ok(todoService.getTodos(weather, select1_1, select1_2, pageable));
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.getTodo(todoId));
    }

    @GetMapping("/todos/test")
    public ResponseEntity<List<TestResponseDto>> testAPI(@RequestBody TestRequestDto dto) {
        return ResponseEntity.ok(todoService.test(dto));
    }

//    @GetMapping("/todos") // 페이지 정렬 연습용
//    public ResponseEntity<Page<TodoResponse>> getTodos(
//        @RequestParam(defaultValue = "1") int page,  // 페이지 번호 (기본값: 1)
//        @RequestParam(defaultValue = "10") int size,  // 페이지 크기 (기본값: 10)
//        @RequestParam(required = false) String select1,  // 첫 번째 정렬 기준 (예: weather)
//        @RequestParam(required = false) String select2  // 두 번째 정렬 기준 (예: updatedAt)
//    ) {
//        // 기본 정렬을 적용 (없으면 무정렬)
//        Sort sort = Sort.unsorted();
//
//        // select1이 null이 아니면 해당 값에 따라 정렬 조건 추가
//        if (select1 != null) {
//            sort = Sort.by(select1).descending();
//        }
//
//        // select2가 null이 아니면 두 번째 정렬 기준 추가
//        if (select2 != null) {
//            sort = sort.and(Sort.by(select2).descending());
//        }
//
//        // Pageable 객체 생성 (페이지 번호는 0부터 시작하므로 -1 처리)
//        Pageable pageable = PageRequest.of(page - 1, size, sort);
//
//        // 서비스 호출 및 응답 반환
//        return ResponseEntity.ok(todoService.getTodos(select1, select2, pageable));
//    }

}
