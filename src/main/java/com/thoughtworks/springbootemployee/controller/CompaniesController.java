package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.service.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {
    @Autowired
    CompaniesService companiesService;
    @Autowired
    CompanyMapper companyMapper;

    public CompaniesController(CompaniesService companiesService) {
        this.companiesService = companiesService;
    }

    @GetMapping
    public List<CompanyResponse> getAllCompanies() {
        return companyMapper.toResponse(companiesService.getAllCompanies());
    }

    @GetMapping(path = "/{companyId}")
    public CompanyResponse findCompanyById(@PathVariable Integer companyId) {
        return companyMapper.toResponse(companiesService.getCompanyById(companyId));
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> findCompanyEmployeesById(@PathVariable Integer companyId) {
        return companiesService.getCompanyEmployeesById(companyId);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Company> getCompaniesByPagination(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return companiesService.getCompaniesByPagination(pageIndex, pageSize);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompanyResponse addCompany(@RequestBody Company company) {
        return companyMapper.toResponse(companiesService.addCompany(company));
    }

    @PutMapping(path = "/{companyId}")
    public CompanyResponse updateCompany(@PathVariable Integer companyId, @RequestBody Company companyUpdated) {
        return companyMapper.toResponse(companiesService.updateCompany(companyId, companyUpdated));
    }

    @DeleteMapping(path = "/{companyId}")
    public List<Company> deleteCompany(@PathVariable Integer companyId) {
        return companiesService.deleteCompany(companyId);
    }

}
