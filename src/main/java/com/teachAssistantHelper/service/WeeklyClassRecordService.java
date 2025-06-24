package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.*;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordRequestDto;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordResponseDto;
import com.teachAssistantHelper.dto.weeklyClassRecord.WeeklyClassRecordWithStudentResponseDto;
import com.teachAssistantHelper.exception.*;
import com.teachAssistantHelper.exception.domainException.ClassEntityException;
import com.teachAssistantHelper.exception.domainException.StaffException;
import com.teachAssistantHelper.exception.domainException.StudentException;
import com.teachAssistantHelper.exception.domainException.WeeklyClassRecordException;
import com.teachAssistantHelper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyClassRecordService {

    private final WeeklyClassRecordRepository recordRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final StaffRepository staffRepository;
    private final WeeklyClassRecordRepository weeklyClassRecordRepository;

    public WeeklyClassRecordResponseDto create(WeeklyClassRecordRequestDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));
        Staff createdBy = staffRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));
        Staff updatedBy = staffRepository.findById(dto.getUpdatedById())
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));

        WeeklyClassRecord record = WeeklyClassRecord.builder()
                .student(student)
                .classEntity(classEntity)
                .weekNo(dto.getWeekNo())
                .attended(dto.isAttended())
                .testScore(dto.getTestScore())
                .homeworkScore(dto.getHomeworkScore())
                .note(dto.getNote())
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .build();

        return new WeeklyClassRecordResponseDto(recordRepository.save(record));
    }

    public List<WeeklyClassRecordResponseDto> getAll() {
        return recordRepository.findAll().stream()
                .map(WeeklyClassRecordResponseDto::new)
                .collect(Collectors.toList());
    }

    public WeeklyClassRecordResponseDto getById(Long id) {
        WeeklyClassRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new WeeklyClassRecordException(ErrorCode.WEEKLY_RECORD_NOT_FOUND));
        return new WeeklyClassRecordResponseDto(record);
    }

    public WeeklyClassRecordResponseDto update(Long id, WeeklyClassRecordRequestDto dto) {
        WeeklyClassRecord existing = recordRepository.findById(id)
                .orElseThrow(() -> new WeeklyClassRecordException(ErrorCode.WEEKLY_RECORD_NOT_FOUND));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));
        Staff updatedBy = staffRepository.findById(dto.getUpdatedById())
                .orElseThrow(() -> new StaffException(ErrorCode.STAFF_NOT_FOUND));

        WeeklyClassRecord updated = WeeklyClassRecord.builder()
                .id(existing.getId())
                .student(student)
                .classEntity(classEntity)
                .weekNo(dto.getWeekNo())
                .attended(dto.isAttended())
                .testScore(dto.getTestScore())
                .homeworkScore(dto.getHomeworkScore())
                .note(dto.getNote())
                .createdBy(existing.getCreatedBy())  // 생성자는 그대로 유지
                .updatedBy(updatedBy)
                .createdAt(existing.getCreatedAt())  // 시간도 유지
                .build();

        return new WeeklyClassRecordResponseDto(recordRepository.save(updated));
    }

    public void delete(Long id) {
        if (!recordRepository.existsById(id)) {
            throw new WeeklyClassRecordException(ErrorCode.WEEKLY_RECORD_NOT_FOUND);
        }
        recordRepository.deleteById(id);
    }

    /** week, class에 맞는 데이터만 검색해서 학생 데이터와 함께 반환*/
    public List<WeeklyClassRecordWithStudentResponseDto> getWithStudentByWeekAndClass(Long classId, int weekNo) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(()-> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        return recordRepository.getWithStudentWeeklyClassRecordsByClassEntityAndWeekNo(classEntity, weekNo)
                .parallelStream().map(WeeklyClassRecordWithStudentResponseDto::new).toList();
    }

    /** 해당 수업의 weekNo 검색해서 List로 반환 */
    public List<Integer> getWeekNoListByClass(Long classId) {
        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(()->new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        return recordRepository.findWeekNosByClassEntity(classEntity);
    }

}
