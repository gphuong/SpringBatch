package com.phuongheh.spring.batch.demo14.listener;

import com.phuongheh.spring.batch.demo14.dto.EmployeeDTO;
import com.phuongheh.spring.batch.demo14.model.Employee;
import org.springframework.batch.core.ItemProcessListener;

public class ProcessListener implements ItemProcessListener<EmployeeDTO, Employee> {
    @Override
    public void beforeProcess(EmployeeDTO employeeDTO) {
        System.out.println("Before process :" + employeeDTO.toString());
    }

    @Override
    public void afterProcess(EmployeeDTO employeeDTO, Employee employee) {
        System.out.println("After process : " + employee.toString());
    }

    @Override
    public void onProcessError(EmployeeDTO employeeDTO, Exception e) {
        System.out.println("On error :" + e.getMessage());
    }
}
