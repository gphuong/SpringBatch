package com.phuongheh.batch.demo6.processor;


import com.phuongheh.batch.demo6.dto.EmployeeDTO;
import com.phuongheh.batch.demo6.model.Employee;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee> {

    private ExecutionContext executionContext;

    public EmployeeProcessor(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    @Override
    public Employee process(EmployeeDTO employeeDTO) throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId() + executionContext.getString("customFileName"));
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAge(employeeDTO.getAge());
        System.out.println("inside processor " + employee.toString());
        return employee;
    }
}
