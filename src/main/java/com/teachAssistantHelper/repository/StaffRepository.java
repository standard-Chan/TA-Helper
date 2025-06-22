package com.teachAssistantHelper.repository;

import com.teachAssistantHelper.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}

