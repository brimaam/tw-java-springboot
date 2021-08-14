package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompaniesService {
    @Autowired
    private final CompaniesRepository companiesRepository;

    public CompaniesService(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    public List<Company> getAllCompanies() {
        return companiesRepository.findAll();
    }

    public Company getCompanyById(Integer companyId) {
        return companiesRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found."));
    }

    public List<Employee> getCompanyEmployeesById(Integer companyId) {
        return companiesRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found.")).getEmployees();
    }

    public List<Company> getCompaniesByPagination(Integer pageIndex, Integer pageSize) {
        return companiesRepository.findAll(PageRequest.of((pageIndex - 1), pageSize)).getContent();
    }

    public Company addCompany(Company company) {
        return companiesRepository.save(company);
    }

    public Company updateCompany(Integer employeeId, Company companyUpdated) {
        Company company = companiesRepository.findById(employeeId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found."));
        assert company != null;

        if (companyUpdated.getCompanyName() != null) {
            company.setCompanyName(companyUpdated.getCompanyName());
        }
        return companiesRepository.save(company);
    }

    public List<Company> deleteCompany(Integer companyId) {
        companiesRepository.delete(companiesRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found.")));
        return companiesRepository.findAll();
    }
}
