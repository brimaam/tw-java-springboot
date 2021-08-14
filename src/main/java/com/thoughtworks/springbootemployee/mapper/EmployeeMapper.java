package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeRequest employeeRequest){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);

        return employee;
    }

    public EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);

        return employeeResponse;
    }

    public List<EmployeeResponse> toResponse(List<Employee> employees) {
        List<EmployeeResponse> employeesResponse = new ArrayList<>();
        BeanUtils.copyProperties(employees, employeesResponse);

        for (Employee employee : employees) {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            BeanUtils.copyProperties(employee, employeeResponse);
            employeesResponse.add(employeeResponse);
        }

        return employeesResponse;
    }
}
