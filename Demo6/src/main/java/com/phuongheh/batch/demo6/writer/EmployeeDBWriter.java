package com.phuongheh.batch.demo6.writer;

import com.javacodingskills.spring.batch.demo6.model.Employee;
import com.javacodingskills.spring.batch.demo6.repo.EmployeeRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDBWriter implements ItemWriter<Employee> {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public void write(List<? extends Employee> employees) throws Exception {
        employeeRepo.saveAll(employees);
        System.out.println("inside writer " + employees);
    }
}
