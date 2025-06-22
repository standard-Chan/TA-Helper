package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.*;
import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordRequestDto;
import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordResponseDto;
import com.teachAssistantHelper.exception.*;
import com.teachAssistantHelper.exception.domainException.ExtraClassException;
import com.teachAssistantHelper.exception.domainException.StaffException;
import com.teachAssistantHelper.exception.domainException.StudentException;
import com.teachAssistantHelper.exception.domainException.WeeklyExtraClassRecordException;
import com.teachAssistantHelper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyExtraClassRecordService {

    private final WeeklyExtraClassRecordRepository recordRepository;
    private final StudentRepository studentRepository;
    private final ExtraClassRepository extraClassRepository;
    private final StaffRepository staffRepository;

    public WeeklyExtraClassRecordResponseDto create(WeeklyExtraClassRecordRequestDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ExtraClass extraClass = extraClassRepository.findById(dto.getExtraClassId())
                .orElseThrow(() -> new ExtraClassException(ErrorCode.EXTRA_CLASS_NOT_FOUND));
        Staff createdBy = staffRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));
        Staff updatedBy = staffRepository.findById(dto.getUpdatedById())
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));

        WeeklyExtraClassRecord record = WeeklyExtraClassRecord.builder()
                .student(student)
                .extraClass(extraClass)
                .weekNo(dto.getWeekNo())
                .reason(dto.getReason())
                .attended(dto.isAttended())
                .attendedTime(dto.getAttendedTime())
                .exitTime(dto.getExitTime())
                .testScore(dto.getTestScore())
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .build();

        return new WeeklyExtraClassRecordResponseDto(recordRepository.save(record));
    }

    public List<WeeklyExtraClassRecordResponseDto> getAll() {
        return recordRepository.findAll().stream()
                .map(WeeklyExtraClassRecordResponseDto::new)
                .collect(Collectors.toList());
    }

    public WeeklyExtraClassRecordResponseDto getById(Long id) {
        WeeklyExtraClassRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new WeeklyExtraClassRecordException(ErrorCode.WEEKLY_EXTRA_RECORD_NOT_FOUND));
        return new WeeklyExtraClassRecordResponseDto(record);
    }

    public WeeklyExtraClassRecordResponseDto update(Long id, WeeklyExtraClassRecordRequestDto dto) {
        WeeklyExtraClassRecord existing = recordRepository.findById(id)
                .orElseThrow(() -> new WeeklyExtraClassRecordException(ErrorCode.WEEKLY_EXTRA_RECORD_NOT_FOUND));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ExtraClass extraClass = extraClassRepository.findById(dto.getExtraClassId())
                .orElseThrow(() -> new ExtraClassException(ErrorCode.EXTRA_CLASS_NOT_FOUND));
        Staff updatedBy = staffRepository.findById(dto.getUpdatedById())
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));

        WeeklyExtraClassRecord updated = WeeklyExtraClassRecord.builder()
                .id(existing.getId())
                .student(student)
                .extraClass(extraClass)
                .weekNo(dto.getWeekNo())
                .reason(dto.getReason())
                .attended(dto.isAttended())
                .attendedTime(dto.getAttendedTime())
                .exitTime(dto.getExitTime())
                .testScore(dto.getTestScore())
                .createdBy(existing.getCreatedBy())
                .updatedBy(updatedBy)
                .createdAt(existing.getCreatedAt())
                .build();

        return new WeeklyExtraClassRecordResponseDto(recordRepository.save(updated));
    }

    public void delete(Long id) {
        if (!recordRepository.existsById(id)) {
            throw new WeeklyExtraClassRecordException(ErrorCode.WEEKLY_EXTRA_RECORD_NOT_FOUND);
        }
        recordRepository.deleteById(id);
    }
}

