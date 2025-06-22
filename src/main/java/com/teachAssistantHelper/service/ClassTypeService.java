package com.teachAssistantHelper.service;

import com.teachAssistantHelper.domain.ClassType;
import com.teachAssistantHelper.dto.ClassTypeRequestDto;
import com.teachAssistantHelper.dto.ClassTypeResponseDto;
import com.teachAssistantHelper.repository.ClassTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassTypeService {

    private final ClassTypeRepository classTypeRepository;

    public ClassTypeResponseDto createClassType(ClassTypeRequestDto dto) {
        ClassType classType = ClassType.builder()
                .name(dto.getName())
                .book(dto.getBook())
                .test(dto.getTest())
                .homework(dto.getHomework())
                .build();

        return new ClassTypeResponseDto(classTypeRepository.save(classType));
    }

    public List<ClassTypeResponseDto> getAllClassTypes() {
        return classTypeRepository.findAll().stream()
                .map(ClassTypeResponseDto::new)
                .collect(Collectors.toList());
    }
}
