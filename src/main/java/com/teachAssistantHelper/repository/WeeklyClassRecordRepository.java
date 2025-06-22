package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.WeeklyClassRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyClassRecordRepository extends JpaRepository<WeeklyClassRecord, Long> {
}

