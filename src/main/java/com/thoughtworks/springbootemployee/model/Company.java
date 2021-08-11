package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
    private Integer id;
    private String companyName;
    private List<Employee> employees;

    public Company() {
    }

    public Company(Integer id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
