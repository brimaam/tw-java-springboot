package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
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
    @Autowired
    EmployeeMapper employeeMapper;

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
    public List<EmployeeResponse> findCompanyEmployeesById(@PathVariable Integer companyId) {
        return employeeMapper.toResponse(companiesService.getCompanyEmployeesById(companyId));
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<CompanyResponse> getCompaniesByPagination(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return companyMapper.toResponse(companiesService.getCompaniesByPagination(pageIndex, pageSize));
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
    public List<CompanyResponse> deleteCompany(@PathVariable Integer companyId) {
        return companyMapper.toResponse(companiesService.deleteCompany(companyId));
    }

}
