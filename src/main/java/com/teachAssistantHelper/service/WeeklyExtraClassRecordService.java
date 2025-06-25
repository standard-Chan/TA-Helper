package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.*;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordRequestDto;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordResponseDto;
import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordRequestDto;
import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordResponseDto;
import com.teachAssistantHelper.dto.weeklyExtraClassRecord.WeeklyExtraClassRecordWithStudentResponseDto;
import com.teachAssistantHelper.exception.*;
import com.teachAssistantHelper.exception.domainException.ExtraClassException;
import com.teachAssistantHelper.exception.domainException.StaffException;
import com.teachAssistantHelper.exception.domainException.StudentException;
import com.teachAssistantHelper.exception.domainException.WeeklyExtraClassRecordException;
import com.teachAssistantHelper.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeeklyExtraClassRecordService {

    private final WeeklyExtraClassRecordRepository recordRepository;
    private final StudentRepository studentRepository;
    private final ExtraClassRepository extraClassRepository;
    private final StaffRepository staffRepository;

    public WeeklyExtraClassRecordResponseDto create(WeeklyExtraClassRecordRequestDto dto, Staff staff) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ExtraClass extraClass = extraClassRepository.findById(dto.getExtraClassId())
                .orElseThrow(() -> new ExtraClassException(ErrorCode.EXTRA_CLASS_NOT_FOUND));

        WeeklyExtraClassRecord record = WeeklyExtraClassRecord.builder()
                .student(student)
                .extraClass(extraClass)
                .weekNo(dto.getWeekNo())
                .reason(dto.getReason())
                .attended(dto.isAttended())
                .attendedTime(dto.getAttendedTime())
                .exitTime(dto.getExitTime())
                .testScore(dto.getTestScore())
                .createdBy(staff)
                .updatedBy(staff)
                .build();

        return new WeeklyExtraClassRecordResponseDto(recordRepository.save(record));
    }

    public List<WeeklyExtraClassRecordWithStudentResponseDto> getWithStudentByWeekAndExtraClass(Long extraClassId, int weekNo) {
        ExtraClass extraClass = extraClassRepository.findById(extraClassId)
                .orElseThrow(() -> new ExtraClassException(ErrorCode.EXTRA_CLASS_NOT_FOUND));

        return recordRepository.getWithStudentByExtraClassAndWeekNo(extraClass, weekNo)
                .stream().map(WeeklyExtraClassRecordWithStudentResponseDto::new).toList();
    }

    public WeeklyExtraClassRecordResponseDto getById(Long id) {
        WeeklyExtraClassRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new WeeklyExtraClassRecordException(ErrorCode.WEEKLY_EXTRA_RECORD_NOT_FOUND));
        return new WeeklyExtraClassRecordResponseDto(record);
    }

    public WeeklyExtraClassRecordResponseDto update(Long classId, WeeklyExtraClassRecordRequestDto dto, Staff staff) {
        WeeklyExtraClassRecord existing = recordRepository.findById(classId)
                .orElseThrow(() -> new WeeklyExtraClassRecordException(ErrorCode.WEEKLY_EXTRA_RECORD_NOT_FOUND));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ExtraClass extraClass = extraClassRepository.findById(dto.getExtraClassId())
                .orElseThrow(() -> new ExtraClassException(ErrorCode.EXTRA_CLASS_NOT_FOUND));

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
                .updatedBy(staff)
                .build();

        return new WeeklyExtraClassRecordResponseDto(recordRepository.save(updated));
    }

    public void delete(Long id) {
        if (!recordRepository.existsById(id)) {
            throw new WeeklyExtraClassRecordException(ErrorCode.WEEKLY_EXTRA_RECORD_NOT_FOUND);
        }
        recordRepository.deleteById(id);
    }

    /** class ID와 week에 해당하는 주간 기록 얻는 메서드*/
    public List<Integer> getWeekNoByExtraClass(Long extraClassId) {
        ExtraClass extraClass = extraClassRepository.findById(extraClassId)
                .orElseThrow(() -> new ExtraClassException(ErrorCode.EXTRA_CLASS_NOT_FOUND));

        return recordRepository.getWeekNoListByExtraClass(extraClass);
    }

    /** 데이터가 존재하면 UPDATE, 데이터가 없으면 INSERT*/
    @Transactional
    public WeeklyExtraClassRecordResponseDto upsert(WeeklyExtraClassRecordRequestDto dto, Staff staff) {
        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(()-> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        Optional<WeeklyExtraClassRecord> record = recordRepository.findByStudentAndWeekNo(student, dto.getWeekNo());

        // 이미 존재하는 경우
        if (record.isPresent()) { // UPDATE
            return update(record.get().getId(), dto, staff);
        } else { // INSERT
            return create(dto, staff);
        }
    }
}

