package com.phuongheh.spring.batch.demo7.repo;

import com.phuongheh.spring.batch.demo7.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
}
