package com.imaginnovate.demo.controller;

import com.imaginnovate.demo.dto.EmployeeDto;
import com.imaginnovate.demo.dto.StandardResponse;
import com.imaginnovate.demo.dto.TaxDetails;
import com.imaginnovate.demo.entity.Employee;
import com.imaginnovate.demo.repository.EmployeeRepo;
import com.imaginnovate.demo.service.DataService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static com.imaginnovate.demo.service.DataService.validateEmployee;


@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepo employeeRepo;
    @Autowired
    private DataService dataService;

    @PostMapping("/register")
    @ResponseBody
    public StandardResponse register(@RequestBody EmployeeDto employeeDto) {
        try{
            Employee entity = null;
            String validationErrors = validateEmployee(employeeDto);
            if (!validationErrors.isEmpty()) {
                return new StandardResponse(0,"Employee validation failed: " + validationErrors);
            }
            if (employeeDto.getEmployeeId() != null){
                Optional<Employee> entity1 = employeeRepo.findById(employeeDto.getEmployeeId());
                entity = entity1.get();
            } else{
                entity = new Employee();
            }
            ModelMapper modelMapper = new ModelMapper();
            entity = modelMapper.map(employeeDto, Employee.class);
            employeeRepo.save(entity);
            return new StandardResponse(1, "Success");
        }catch (Exception e){
            e.printStackTrace();
            return new StandardResponse(0,e.getMessage());
        }
    }

    @GetMapping("/get-tax-details/{employeeId}")
    @ResponseBody
    public TaxDetails getTaxDetails(@PathVariable Integer employeeId) {
        Employee employee = null;
        TaxDetails details = new TaxDetails();
        try{
            if (employeeId != null){
                Optional<Employee> entity = employeeRepo.findById(employeeId);
                if (entity.isPresent())
                    employee = entity.get();
                else
                    return details;
                double monthlySalary = employee.getSalary();
                LocalDate doj = LocalDateTime.ofInstant(employee.getDoj().toInstant(), ZoneId.systemDefault()).toLocalDate();
                double yearlySalary = dataService.calculateYearlySalary(monthlySalary, doj);
                ModelMapper modelMapper = new ModelMapper();
                details = modelMapper.map(employee, TaxDetails.class);
                details.setYearlySalary(yearlySalary);
                details.setTotalTax(dataService.calculateTax(yearlySalary));
                details.setCess(dataService.calculateCess(yearlySalary));
                details.setLossOfPayPerDay(employee.getSalary()/30);
                return details;
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
