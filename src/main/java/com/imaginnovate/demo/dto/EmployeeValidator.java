package com.imaginnovate.demo.dto;

import java.util.Date;
import java.util.regex.Pattern;

public class EmployeeValidator {

    // Validating the employee ID
    public static boolean isValidEmployeeId(Integer employeeId) {
        return employeeId != null && employeeId > 0;
    }

    // Validating the first name
    public static boolean isValidFirstName(String firstName) {
        return firstName != null && !firstName.trim().isEmpty() && firstName.matches("[a-zA-Z]+");
    }

    // Validating the last name
    public static boolean isValidLastName(String lastName) {
        return lastName != null && !lastName.trim().isEmpty() && lastName.matches("[a-zA-Z]+");
    }

    // Validating the email address
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    // Validating the phone number
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "\\d{10}";
        Pattern pattern = Pattern.compile(phoneRegex);
        return phoneNumber != null && pattern.matcher(phoneNumber).matches();
    }

    // Validating the date of joining
    public static boolean isValidDoj(Date doj) {
        return doj != null && doj.before(new Date());
    }

    // Validating the salary
    public static boolean isValidSalary(Double salary) {
        return salary != null && salary > 0;
    }
}
