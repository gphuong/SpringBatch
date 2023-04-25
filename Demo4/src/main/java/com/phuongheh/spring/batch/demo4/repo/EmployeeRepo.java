package com.phuongheh.spring.batch.demo4.repo;

import com.phuongheh.spring.batch.demo4.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, String> {
}
