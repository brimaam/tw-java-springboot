package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.controller.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class EmployeesServiceTest {
    @InjectMocks
    private EmployeesService employeeService;
    @Mock
    private EmployeesRepository employeeRepository;

    @Test
    public void should_return_all_employees_when_getAllEmployees_given_all_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alice", 20, "female", 2000));
        employees.add(new Employee(2, "bob", 21, "male", 1000));
        given(employeeRepository.getEmployees()).willReturn(employees);

        //when
        List<Employee> actualEmployees = employeeService.getAllEmployees();

        //then
        assertIterableEquals(employees, actualEmployees);
    }

    @Test
    public void should_return_employee_when_findEmployeeById_given_employeeId() {
        //given
        Integer employeeID = 1;
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(employeeID, "alice", 20, "female", 2000));
        employees.add(new Employee(2, "bob", 21, "male", 1000));

        given(employeeRepository.getEmployees()).willReturn(employees);

        //when
        Employee actualEmployee = employeeService.findEmployeeById(employeeID);

        //then
        assertNotNull(actualEmployee);
    }
}
