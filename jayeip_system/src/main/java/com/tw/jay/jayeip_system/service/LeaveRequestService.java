package com.tw.jay.jayeip_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.jay.jayeip_system.DTO.Request.LeaveApproveDto;
import com.tw.jay.jayeip_system.DTO.Request.LeaveRequestCreateDto;
import com.tw.jay.jayeip_system.entity.Employee;
import com.tw.jay.jayeip_system.entity.LeaveRequest;
import com.tw.jay.jayeip_system.repository.EmployeeRepo;
import com.tw.jay.jayeip_system.repository.LeaveRequestRepo;

import jakarta.transaction.Transactional;

@Service
public class LeaveRequestService {
    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    public List<LeaveRequestCreateDto> getAllLeaveRequests() {
       List<LeaveRequest> leaveRequests = leaveRequestRepo.findAll();
         return leaveRequests.stream().map(lea -> {
            LeaveRequestCreateDto dto = new LeaveRequestCreateDto();
                dto.setId(lea.getId());
                dto.setEmployeeId(lea.getEmployee().getId());
                dto.setStartDate(lea.getStartDate());
                dto.setEndDate(lea.getEndDate());
                dto.setReason(lea.getReason());
                dto.setStatus(lea.getStatus());
                return dto;

         }).collect(Collectors.toList());
         
    }

    public void applyLeave(LeaveRequestCreateDto requestDto) {
        //查詢請假員工
        Employee employee = employeeRepo.findById(requestDto.getEmployeeId())
            .orElseThrow(() -> new RuntimeException("此員工不存在"));

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(employee);
        leave.setStartDate(requestDto.getStartDate());
        leave.setEndDate(requestDto.getEndDate());
        leave.setReason(requestDto.getReason());
        leave.setStatus("Pending");

        leaveRequestRepo.save(leave);
        
    }

    //審核假單
    @Transactional
    public void approveLeave(LeaveApproveDto dto) {
        LeaveRequest leave = leaveRequestRepo.findById(dto.getLeaveRequestId())
            .orElseThrow(() -> new RuntimeException("此假單不存在"));
        
        if(!"Pending".equals(leave.getStatus())) {
            throw new RuntimeException("此假單已審核過");
        }

        Employee approver = employeeRepo.findById(dto.getApproverId())
            .orElseThrow(() -> new RuntimeException("此簽核人不存在"));

        leave.setStatus(dto.getStatus());
        leave.setApprover(approver);
        leaveRequestRepo.save(leave);    
    }


  
}
