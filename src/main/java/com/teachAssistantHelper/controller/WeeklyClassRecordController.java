package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordRequestDto;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordResponseDto;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordWithStudentResponseDto;
import com.teachAssistantHelper.service.WeeklyClassRecordService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-records")
@RequiredArgsConstructor
public class WeeklyClassRecordController {

    private final WeeklyClassRecordService recordService;

    @PostMapping
    public ResponseEntity<WeeklyClassRecordResponseDto> create(@RequestBody WeeklyClassRecordRequestDto dto) {
        return ResponseEntity.ok(recordService.create(dto));
    }

//    @GetMapping
//    public ResponseEntity<List<WeeklyClassRecordResponseDto>> getAll() {
//        return ResponseEntity.ok(recordService.getAll());
//    }

    @GetMapping()
    public ResponseEntity<List<WeeklyClassRecordWithStudentResponseDto>> getWithStudentByWeekAndClass(@RequestParam("class") Long classId,
                                                                                                      @RequestParam("week") int weekNo) {
        return ResponseEntity.ok(recordService.getWithStudentByWeekAndClass(classId, weekNo));
    }

    @GetMapping("/class/{classId}/week")
    public ResponseEntity<List<Integer>> getWeekNoListByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(recordService.getWeekNoListByClass(classId));
    }



    @GetMapping("/{id}")
    public ResponseEntity<WeeklyClassRecordResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeeklyClassRecordResponseDto> update(
            @PathVariable Long id,
            @RequestBody WeeklyClassRecordRequestDto dto
    ) {
        return ResponseEntity.ok(recordService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        recordService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }
}

