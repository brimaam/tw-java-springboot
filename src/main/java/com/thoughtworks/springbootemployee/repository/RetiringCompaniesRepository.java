package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RetiringCompaniesRepository {
    private final List<Company> companies = new ArrayList<>();

    public RetiringCompaniesRepository() {
        List<Employee> morningEmployees = new ArrayList<>();
        morningEmployees.add(new Employee(1, "allie", 22, "female", 2000));
        morningEmployees.add(new Employee(2, "diego", 21, "male", 3000));
        morningEmployees.add(new Employee(3, "sally", 25, "female", 1400));

        List<Employee> nightEmployees = new ArrayList<>();
        nightEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        nightEmployees.add(new Employee(2, "franco", 21, "male", 1000));
        nightEmployees.add(new Employee(3, "kyle", 25, "male", 1200));

        companies.add(new Company(1, "Google", morningEmployees));
        companies.add(new Company(2, "Twitter", nightEmployees));
        companies.add(new Company(3, "Facebook", morningEmployees));
        companies.add(new Company(4, "Adobe", nightEmployees));
        companies.add(new Company(5, "Chevron", morningEmployees));
        companies.add(new Company(6, "Apple", nightEmployees));
        companies.add(new Company(7, "Sony", morningEmployees));
    }

    public List<Company> getCompanies() {
        return companies;
    }
}
