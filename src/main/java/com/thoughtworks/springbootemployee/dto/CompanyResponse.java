package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.entity.Employee;

import java.util.List;

public class CompanyResponse {
    private Integer id;
    private String companyName;
    private Integer employeeNumber;
    private List<Employee> employees;

    public CompanyResponse() {
    }

    public CompanyResponse(Integer id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
    }

    public CompanyResponse(String companyName) {
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
