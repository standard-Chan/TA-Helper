package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}

