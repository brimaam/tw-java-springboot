package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import com.thoughtworks.springbootemployee.repository.RetiringEmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeesService {
    @Autowired
    private RetiringEmployeesRepository retiringEmployeesRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public List<Employee> getAllEmployees() {
        return employeesRepository.findAll();
    }

    public Employee findEmployeeById(Integer employeeId) {
        return employeesRepository.findById(employeeId).orElse(null);
    }

    public List<Employee> getEmployeesByPagination(Integer pageIndex, Integer pageSize) {
        return employeesRepository.findAll(PageRequest.of((pageIndex - 1),pageSize)).getContent();
    }

    public List<Employee> getAllEmployeesByGender(String gender) {
        return employeesRepository.findAllByGender(gender);
    }

    public void addEmployee(Employee employee) {
        employeesRepository.save(employee);
    }

    public Employee updateEmployee(Integer employeeId, Employee employeeUpdated) {
        Employee employee = employeesRepository.findById(employeeId).orElse(null);

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
        employeesRepository.save(employee);
        return employee;
    }

    public void deleteEmployeeRecord(Integer employeeId) {
        employeesRepository.delete(Objects.requireNonNull(employeesRepository.findById(employeeId).orElse(null)));
    }

}
