package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {
    @Autowired
    CompaniesService companiesService;

    public CompaniesController(CompaniesService companiesService) {
        this.companiesService = companiesService;
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companiesService.getAllCompanies();
    }

    @GetMapping(path = "/{companyId}")
    public Company findCompanyById(@PathVariable Integer companyId) {
        return companiesService.getCompanyById(companyId);
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> findCompanyEmployeesById(@PathVariable Integer companyId) {
        return companiesService.getCompanyEmployeesById(companyId);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Company> getCompaniesByPagination(@RequestParam Integer pageIndex, @RequestParam Integer pageSize){
        return  companiesService.getCompaniesByPagination(pageIndex,pageSize);
    }

   @PostMapping
    public List<Company> addCompany(@RequestBody Company company){
        return companiesService.addCompany(company);
    }

}
