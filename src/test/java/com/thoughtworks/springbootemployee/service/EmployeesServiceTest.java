package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
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
        List<Employee> actualEmployees = employeeService.getEmployeesByPagination(pageIndex, pageSize);

        //then
        assertIterableEquals(employees, actualEmployees);
    }

    @Test
    public void should_return_all_male_employees_when_getAllEmployeesByGender_given_gender() {
        //given
        String gender = "male";
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(2, "bob", 21, "male", 1000));
        employees.add(new Employee(3, "tom", 25, "male", 1400));
        employees.add(new Employee(4, "jeff", 31, "male", 12100));
        employees.add(new Employee(6, "dave", 19, "male", 1400));
        employees.add(new Employee(7, "cleon", 23, "male", 1600));
        given(employeeRepository.getEmployees()).willReturn(employees);

        //when
        List<Employee> actualEmployees = employeeService.getAllEmployeesByGender(gender);

        //then
        assertIterableEquals(employees, actualEmployees);
    }

    @Test
    public void should_add_an_employee_to_employees_when_addEmployee_given_an_employee() {
        //given
        Integer generatedId = employeeRepository.getEmployees().size() + 1;
        Employee employee = new Employee(generatedId, "john", 43, "male", 6000);

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alice", 20, "female", 2000));
        employees.add(new Employee(2, "bob", 21, "male", 1000));
        given(employeeRepository.getEmployees()).willReturn(employees);

        //when
        employeeService.addEmployee(employee);

        //then
        assertTrue(employees.stream().anyMatch(emp -> emp.getId().equals(employee.getId())));
    }

    @Test
    public void should_update_an_employee_when_updateEmployee_given_an_employeeID_and_employee_update() {
        //given
        Integer employeeID = 1;
        Employee employee = new Employee();
        employee.setName("malice");
        employee.setAge(43);

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alice", 20, "female", 2000));
        employees.add(new Employee(2, "bob", 21, "male", 1000));
        given(employeeRepository.getEmployees()).willReturn(employees);

        //when
        employeeService.updateEmployee(employeeID, employee);

        //then
        assertEquals(employeeService.findEmployeeById(employeeID).getName(), employee.getName());
        assertEquals(employeeService.findEmployeeById(employeeID).getAge(), employee.getAge());
    }

    @Test
    public void should_delete_an_employee_when_deleteEmployee_given_an_employeeID() {
        //given
        Integer employeeId = 1;

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alice", 20, "female", 2000));
        employees.add(new Employee(2, "bob", 21, "male", 1000));
        given(employeeRepository.getEmployees()).willReturn(employees);

        //when
        employeeService.deleteEmployeeRecord(employeeId);

        //then
        assertFalse(employees.stream().anyMatch(emp -> emp.getId().equals(employeeId)));
    }
}
