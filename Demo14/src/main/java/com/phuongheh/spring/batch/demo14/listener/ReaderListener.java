package com.phuongheh.spring.batch.demo14.listener;

import com.phuongheh.spring.batch.demo14.dto.EmployeeDTO;
import org.springframework.batch.core.ItemReadListener;

public class ReaderListener implements ItemReadListener<EmployeeDTO> {

    @Override
    public void beforeRead() {
        System.out.println("Before Read Operation.");
    }

    @Override
    public void afterRead(EmployeeDTO employeeDTO) {
        System.out.println("After Reading :" + employeeDTO.toString());
    }

    @Override
    public void onReadError(Exception e) {
        System.out.println("On error while reading :" + e.getMessage());
    }
}
