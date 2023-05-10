package com.phuongheh.spring.batch.demo16.repo;

import com.phuongheh.spring.batch.demo16.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
}
