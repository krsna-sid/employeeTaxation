package com.imaginnovate.demo.service;

import com.imaginnovate.demo.dto.EmployeeDto;
import com.imaginnovate.demo.dto.EmployeeValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class DataService {
    public static String validateEmployee(EmployeeDto employee) {
        StringBuilder errors = new StringBuilder();

        if (!EmployeeValidator.isValidFirstName(employee.getFirstName())) {
            errors.append("Invalid First Name. ");
        }
        if (!EmployeeValidator.isValidLastName(employee.getLastName())) {
            errors.append("Invalid Last Name. ");
        }
        if (!EmployeeValidator.isValidEmail(employee.getEmail())) {
            errors.append("Invalid Email. ");
        }
        if (!EmployeeValidator.isValidPhoneNumber(employee.getPhoneNumber())) {
            errors.append("Invalid Phone Number. ");
        }
        if (!EmployeeValidator.isValidDoj(employee.getDoj())) {
            errors.append("Invalid Date of Joining. ");
        }
        if (!EmployeeValidator.isValidSalary(employee.getSalary())) {
            errors.append("Invalid Salary. ");
        }
        return errors.toString().trim();
    }


    public double calculateYearlySalary(double monthlySalary, LocalDate doj) {
        LocalDate financialYearStart = getCurrentFinancialYearStart();
        LocalDate financialYearEnd = getCurrentFinancialYearEnd();

        // Calculate total salary based on DOJ and financial year
        if (doj.isBefore(financialYearStart) || doj.isAfter(financialYearEnd)) {
            // Joined before or after the current financial year
            // Return the full year salary for previous years
            return 12 * monthlySalary;
        } else if (doj.isAfter(financialYearStart.minusDays(1)) && doj.isBefore(financialYearEnd.plusDays(1))) {
            // Joined in the current financial year
            return calculatePartialYearSalary(monthlySalary, doj, financialYearStart, financialYearEnd);
        } else {
            // Default case (shouldn't happen)
            return 0;
        }
    }

    private double calculatePartialYearSalary(double monthlySalary, LocalDate doj, LocalDate financialYearStart, LocalDate financialYearEnd) {
        long totalDaysInFinancialYear = ChronoUnit.DAYS.between(financialYearStart, financialYearEnd) + 1;
        long daysWorked = ChronoUnit.DAYS.between(doj, financialYearEnd) + 1;

        // Calculate the monthly salary for the partial year
        return monthlySalary * (daysWorked / (double) totalDaysInFinancialYear);
    }

    public double calculateTax(double yearlySalary) {
        double tax = 0;
        if (yearlySalary > 250000) {
            if (yearlySalary <= 500000) {
                tax += (yearlySalary - 250000) * 0.05;
            } else if (yearlySalary <= 1000000) {
                tax += (500000 - 250000) * 0.05;
                tax += (yearlySalary - 500000) * 0.10;
            } else {
                tax += (500000 - 250000) * 0.05;
                tax += (1000000 - 500000) * 0.10;
                tax += (yearlySalary - 1000000) * 0.20;
            }
        }
        return tax;
    }

    public double calculateCess(double yearlySalary) {
        double cess = 0;
        if (yearlySalary > 2500000) {
            cess = (yearlySalary - 2500000) * 0.02;
        }
        return cess;
    }

    public static LocalDate getCurrentFinancialYearStart() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        if (currentDate.getMonthValue() < 4) { // Before April
            year--;
        }
        return LocalDate.of(year, Month.APRIL, 1);
    }

    // Get the end of the current financial year based on the current date
    public static LocalDate getCurrentFinancialYearEnd() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        if (currentDate.getMonthValue() >= 4) { // From April onwards
            year++;
        }
        return LocalDate.of(year, Month.MARCH, 31);
    }
}
