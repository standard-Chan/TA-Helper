package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.ClassTypeRequestDto;
import com.teachAssistantHelper.dto.ClassTypeResponseDto;
import com.teachAssistantHelper.service.ClassTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-types")
@RequiredArgsConstructor
public class ClassTypeController {

    private final ClassTypeService classTypeService;

    @PostMapping
    public ResponseEntity<ClassTypeResponseDto> createClassType(@RequestBody ClassTypeRequestDto dto) {
        return ResponseEntity.ok(classTypeService.createClassType(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClassTypeResponseDto>> getAllClassTypes() {
        return ResponseEntity.ok(classTypeService.getAllClassTypes());
    }
}
