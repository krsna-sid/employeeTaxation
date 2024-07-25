package com.imaginnovate.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer employeeId;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Date doj;
    Double salary;
}
