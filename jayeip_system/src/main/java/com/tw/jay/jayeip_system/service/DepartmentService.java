package com.tw.jay.jayeip_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.jay.jayeip_system.DTO.Request.DepartmentAdd;
import com.tw.jay.jayeip_system.DTO.Response.DepartmentDto;
import com.tw.jay.jayeip_system.entity.Department;
import com.tw.jay.jayeip_system.repository.DepartmentRepo;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    public List<DepartmentDto> getAllDepartment(){
        List<Department> departments = departmentRepo.findAll();
        return departments.stream().map(dep -> {
            DepartmentDto dto = new DepartmentDto();
                dto.setId(dep.getId());
                dto.setDeptName(dep.getDeptName());
                dto.setTotalEmployees(dep.getEmployees().size());

                return dto;
        }).toList();

        
    }

    public void addDepartment(DepartmentAdd request) {
        Department department = new Department();
        department.setDeptName(request.getDeptName());
        departmentRepo.save(department);
    }

}
