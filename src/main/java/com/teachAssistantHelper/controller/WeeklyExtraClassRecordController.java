package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordRequestDto;
import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordResponseDto;
import com.teachAssistantHelper.service.WeeklyExtraClassRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-extra-records")
@RequiredArgsConstructor
public class WeeklyExtraClassRecordController {

    private final WeeklyExtraClassRecordService recordService;

    @PostMapping
    public ResponseEntity<WeeklyExtraClassRecordResponseDto> create(@RequestBody WeeklyExtraClassRecordRequestDto dto) {
        return ResponseEntity.ok(recordService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<WeeklyExtraClassRecordResponseDto>> getAll() {
        return ResponseEntity.ok(recordService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeeklyExtraClassRecordResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeeklyExtraClassRecordResponseDto> update(@PathVariable Long id,
                                                                    @RequestBody WeeklyExtraClassRecordRequestDto dto) {
        return ResponseEntity.ok(recordService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        recordService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }
}

