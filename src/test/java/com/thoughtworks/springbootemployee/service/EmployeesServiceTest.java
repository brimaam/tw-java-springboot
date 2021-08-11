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
        assertEquals(employeeID, actualEmployee.getId());
        assertNotNull(actualEmployee);
    }

    @Test
    public void should_return_employees_with_page_index_and_size_when_getEmployeesByPagination_given_page_index_and_size() {
        //given
        Integer pageIndex = 1;
        Integer pageSize = 5;
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alice", 22, "female", 2000));
        employees.add(new Employee(2, "bob", 21, "male", 1000));
        employees.add(new Employee(3, "tom", 25, "male", 1400));
        employees.add(new Employee(4, "jeff", 31, "male", 12100));
        employees.add(new Employee(5, "kim", 21, "female", 3000));
        given(employeeRepository.getEmployees()).willReturn(employees);

        //when
        List<Employee>  actualEmployees = employeeService.getEmployeesByPagination(pageIndex,pageSize);

        //then
        assertIterableEquals(employees, actualEmployees);
    }
}
