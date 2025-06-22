package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.student.StudentRequestDto;
import com.teachAssistantHelper.dto.student.StudentResponseDto;
import com.teachAssistantHelper.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDto> create(@RequestBody StudentRequestDto dto) {
        return ResponseEntity.ok(studentService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> update(
            @PathVariable Long id,
            @RequestBody StudentRequestDto dto
    ) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }
}

