package com.tw.jay.jayeip_system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tw.jay.jayeip_system.DTO.Request.EmployeeCreate;
import com.tw.jay.jayeip_system.DTO.Request.EmployeeUpdate;
import com.tw.jay.jayeip_system.DTO.Response.EmployeeDto;
import com.tw.jay.jayeip_system.entity.Department;
import com.tw.jay.jayeip_system.entity.Employee;
import com.tw.jay.jayeip_system.repository.DepartmentRepo;
import com.tw.jay.jayeip_system.repository.EmployeeRepo;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    
    @Autowired
    private DepartmentRepo departmentRepo;

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();

        return employees.stream().map(emp ->{
            EmployeeDto dto = new EmployeeDto();
            dto.setId(emp.getId());
            dto.setName(emp.getName());
            dto.setEmail(emp.getEmail());
            dto.setPosition(emp.getPosition());
            dto.setApproverId(emp.getApproverId());
        

            if(emp.getDepartment() != null) {
                dto.setDepartmentName(emp.getDepartment().getDeptName());
            }
            return dto;
        }).collect(Collectors.toList());
    }


    public void addEmployee(EmployeeCreate employee) {
        Department dept = null;
        if (employee.getDepartmentId() != null) {
            dept = departmentRepo.findById(employee.getDepartmentId()).orElse(null);
        }
        Employee emp = new Employee();
        emp.setName(employee.getName());
        emp.setEmail(employee.getEmail());
        emp.setPosition(employee.getPosition());

        if (dept != null) {
        emp.setDepartment(dept);
        }

        if (employee.getDepartmentId() != null) {
            emp.setDepartment(
                departmentRepo.findById(employee.getDepartmentId()).orElse(null)
            );
        }

        if (employee.getApproverId() != null) {
            emp.setApproverId(
                employee.getApproverId()
            );
        }

        employeeRepo.save(emp);

        if (dept != null) {
        int currentCount = dept.getTotalEmployees();
        dept.setTotalEmployees(currentCount + 1);
        departmentRepo.save(dept);
        }
    }

    public void deleteEmployee(Long id) {
        Employee emp = employeeRepo.findById(id).orElse(null);
        if (emp != null) {
        Department dept = emp.getDepartment();
        employeeRepo.delete(emp);

        if (dept != null) {
            dept.setTotalEmployees(dept.getTotalEmployees() - 1);
            departmentRepo.save(dept);
            }
        }
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee emp = employeeRepo.findById(id).orElse(null);
        if (emp == null) {
            return null;
        }

        EmployeeDto dto = new EmployeeDto();
        dto.setId(emp.getId());
        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        dto.setPosition(emp.getPosition());
        dto.setApproverId(emp.getApproverId());

        if (emp.getDepartment() != null) {
            dto.setDepartmentName(emp.getDepartment().getDeptName());
        }

        return dto;
    }


    public void updateEmployee(Long id, EmployeeUpdate employeeUpdate) {
        Employee emp = employeeRepo.findById(id).orElse(null);
        if (emp == null) {
            return;
        }

        emp.setName(employeeUpdate.getName());
        emp.setEmail(employeeUpdate.getEmail());
        emp.setPosition(employeeUpdate.getPosition());

        if (employeeUpdate.getDepartmentId() != null) {
            emp.setDepartment(
                departmentRepo.findById(employeeUpdate.getDepartmentId()).orElse(null)
            );
        } else {
            emp.setDepartment(null);
        }

        if (employeeUpdate.getApproverId() != null) {
            emp.setApproverId(
                employeeUpdate.getApproverId()
            );
        } else {
            emp.setApproverId(null);
        }

        employeeRepo.save(emp);
    }
}
