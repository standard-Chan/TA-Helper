package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.ClassEntity;
import com.teachAssistantHelper.domain.Student;
import com.teachAssistantHelper.dto.student.StudentRequestDto;
import com.teachAssistantHelper.dto.student.StudentResponseDto;
import com.teachAssistantHelper.exception.domainException.ClassEntityException;
import com.teachAssistantHelper.exception.ErrorCode;
import com.teachAssistantHelper.exception.domainException.StudentException;
import com.teachAssistantHelper.repository.ClassRepository;
import com.teachAssistantHelper.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    public StudentResponseDto create(StudentRequestDto dto) {
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        Student student = new Student(dto.getName(), dto.getSchool(), dto.getParentPhoneNumber(),
                dto.getPhoneNumber(), dto.getEmail(), dto.getAge());

        student.addClass(classEntity);

        return new StudentResponseDto(studentRepository.save(student));
    }

    public List<StudentResponseDto> getAll() {
        return studentRepository.findAll().stream()
                .map(StudentResponseDto::new)
                .collect(Collectors.toList());
    }

    public StudentResponseDto getById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        return new StudentResponseDto(student);
    }

    public StudentResponseDto update(Long id, StudentRequestDto dto) {
        Student original = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        ClassEntity classEntity = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ClassEntityException(ErrorCode.CLASS_NOT_FOUND));

        original.setName(dto.getName());
        original.setSchool(dto.getSchool());
        original.setPhoneNumber(dto.getPhoneNumber());
        original.setParentPhoneNumber(dto.getParentPhoneNumber());
        original.setEmail(dto.getEmail());
        original.setAge(dto.getAge());
        original.addClass(classEntity);

        return new StudentResponseDto(studentRepository.save(original));
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentException(ErrorCode.STUDENT_NOT_FOUND);
        }
        studentRepository.deleteById(id);
    }
}

