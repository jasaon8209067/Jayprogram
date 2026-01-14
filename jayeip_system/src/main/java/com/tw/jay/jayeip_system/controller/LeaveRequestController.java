package com.tw.jay.jayeip_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.jay.jayeip_system.DTO.Request.LeaveApproveDto;
import com.tw.jay.jayeip_system.DTO.Request.LeaveRequestCreateDto;
import com.tw.jay.jayeip_system.service.LeaveRequestService;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping
    public ResponseEntity<List<LeaveRequestCreateDto>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
    }

    @PostMapping("/apply")
    public ResponseEntity<Void> applyLeave(
            @RequestBody LeaveRequestCreateDto requestDto) {
                
        leaveRequestService.applyLeave(requestDto);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("{leaveRequestId}/approve")
    public ResponseEntity<Void> approveLeave(
            @RequestBody LeaveApproveDto dto) {
                
        leaveRequestService.approveLeave(dto);
        return ResponseEntity.ok().build();
    }
}
