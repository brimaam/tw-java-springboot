package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeesService {

    @Autowired
    private final EmployeesRepository employeesRepository;

    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeesRepository.findAll();
    }

    public Employee findEmployeeById(Integer employeeId) {
        return employeesRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));
    }

    public List<Employee> getEmployeesByPagination(Integer pageIndex, Integer pageSize) {
        return employeesRepository.findAll(PageRequest.of((pageIndex - 1), pageSize)).getContent();
    }

    public List<Employee> getAllEmployeesByGender(String gender) {
        return employeesRepository.findAllByGender(gender);
    }

    public Employee addEmployee(Employee employee) {
        return employeesRepository.save(employee);
    }

    public Employee updateEmployee(Integer employeeId, Employee employeeUpdated) {
        Employee employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));

        return updateEmployeeInformation(employee, employeeUpdated);
    }

    private Employee updateEmployeeInformation(Employee employee, Employee employeeUpdated) {
        if (employeeUpdated.getAge() != null) {
            employee.setAge(employeeUpdated.getAge());
        }
        if (employeeUpdated.getName() != null) {
            employee.setName(employeeUpdated.getName());
        }
        if (employeeUpdated.getGender() != null) {
            employee.setGender(employeeUpdated.getGender());
        }
        if (employeeUpdated.getSalary() != null) {
            employee.setSalary(employeeUpdated.getSalary());
        }

        return employeesRepository.save(employee);
    }

    public List<Employee> deleteEmployeeRecord(Integer employeeId) {
        employeesRepository.delete(Objects.requireNonNull(employeesRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."))));
        return employeesRepository.findAll();
    }

}
