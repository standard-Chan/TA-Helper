package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.ExtraClass;
import com.teachAssistantHelper.domain.WeeklyExtraClassRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeeklyExtraClassRecordRepository extends JpaRepository<WeeklyExtraClassRecord, Long> {

    @Query("SELECT DISTINCT r.weekNo FROM WeeklyExtraClassRecord r WHERE r.extraClass = :extraClass ORDER BY r.weekNo")
    List<Integer> getWeekNoListByExtraClass(@Param("extraClass") ExtraClass extraClass);
}

