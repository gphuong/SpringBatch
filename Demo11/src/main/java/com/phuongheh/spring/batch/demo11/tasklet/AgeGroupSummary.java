package com.phuongheh.spring.batch.demo11.tasklet;

import com.phuongheh.spring.batch.demo11.dto.EmployeeDTO;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class AgeGroupSummary implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        return null;
    }

    private static EmployeeDTO employeeMapper(String[] record) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(record[0]);
        employeeDTO.setFirstName(record[1]);
        employeeDTO.setLastName(record[2]);
        employeeDTO.setEmail(record[3]);
        employeeDTO.setAge(Integer.parseInt(record[4]));
        return employeeDTO;
    }
}
