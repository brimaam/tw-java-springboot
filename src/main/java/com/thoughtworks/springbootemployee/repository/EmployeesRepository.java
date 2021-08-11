package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.controller.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeesRepository {
    private final List<Employee> employees = new ArrayList<>();

    public EmployeesRepository(){
        employees.add(new Employee(1, "alice", 22, "female", 2000));
        employees.add(new Employee(2, "bob", 21, "male", 1000));
        employees.add(new Employee(3, "tom", 25, "male", 1400));
        employees.add(new Employee(4, "jeff", 31, "male", 12100));
        employees.add(new Employee(5, "kim", 21, "female", 3000));
        employees.add(new Employee(6, "dave", 19, "male", 1400));
        employees.add(new Employee(7, "cleon", 23, "male", 1600));
    }

    public List<Employee> getEmployees() {
        return employees;
    }
    public Employee getEmployeeById(Integer employeeId){
        return employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }
}
