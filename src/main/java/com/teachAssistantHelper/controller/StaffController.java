package com.teachAssistantHelper.controller;

import com.teachAssistantHelper.dto.staff.StaffRequestDto;
import com.teachAssistantHelper.dto.staff.StaffResponseDto;
import com.teachAssistantHelper.dto.staff.StaffRoleUpdateRequestDto;
import com.teachAssistantHelper.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staffs")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping
    public ResponseEntity<List<StaffResponseDto>> getAllStaffs() {
        return ResponseEntity.ok(staffService.getAllStaffs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponseDto> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }


    @PostMapping
    public ResponseEntity<StaffResponseDto> createStaff(@RequestBody StaffRequestDto dto) {
        return ResponseEntity.ok(staffService.createStaff(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<StaffResponseDto> updateRole(
            @PathVariable Long id,
            @RequestBody StaffRoleUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(staffService.updateRole(id, dto));
    }

}
