package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import com.thoughtworks.springbootemployee.repository.RetiringEmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeesService {
    @Autowired
    private final RetiringEmployeesRepository retiringEmployeesRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public EmployeesService(RetiringEmployeesRepository retiringEmployeesRepository) {
        this.retiringEmployeesRepository = retiringEmployeesRepository;
    }

    public List<Employee> getAllEmployees() {
        return retiringEmployeesRepository.getEmployees();
    }

    public Employee findEmployeeById(Integer employeeId) {
        return retiringEmployeesRepository.getEmployees().stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getEmployeesByPagination(Integer pageIndex, Integer pageSize) {
        return retiringEmployeesRepository.getEmployees().stream()
                .skip((long) (pageIndex - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getAllEmployeesByGender(String gender) {
        return retiringEmployeesRepository.getEmployees().stream()
                .filter(employee -> gender.equalsIgnoreCase(employee.getGender()))
                .collect(Collectors.toList());
    }

    public void addEmployee(Employee employee) {
        employeesRepository.save(employee);
    }

    public Employee updateEmployee(Integer employeeId, Employee employeeUpdated) {
        List<Employee> employees = retiringEmployeesRepository.getEmployees();

        return employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .map(employee -> updateEmployeeInformation(employee, employeeUpdated)).orElse(null);
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

        return employee;
    }

    public List<Employee> deleteEmployeeRecord(Integer employeeId) {
        List<Employee> employees = retiringEmployeesRepository.getEmployees();

        employees.removeIf(employee -> employee.getId().equals(employeeId));
        return employees;
    }

}
