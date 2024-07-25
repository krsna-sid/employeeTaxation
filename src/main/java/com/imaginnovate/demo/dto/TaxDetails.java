package com.imaginnovate.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class TaxDetails {
    Integer employeeId;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Date doj;
    Double salary;
    Double yearlySalary;
    Double totalTax;
    Double cess;
    Double LossOfPayPerDay;
}
