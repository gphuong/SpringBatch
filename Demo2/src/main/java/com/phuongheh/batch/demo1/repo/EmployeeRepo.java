package com.phuongheh.batch.demo1.repo;

import com.phuongheh.batch.demo1.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {
}
