package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

