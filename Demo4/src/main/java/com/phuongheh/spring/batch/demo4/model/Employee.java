package com.phuongheh.spring.batch.demo4.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Employee {

    @Id
    private String employeeId;
    private String firstName;
    private String lastName;
    private int age;
    private String email;


}
