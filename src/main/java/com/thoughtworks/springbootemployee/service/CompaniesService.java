package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompaniesRepository;
import com.thoughtworks.springbootemployee.repository.RetiringCompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompaniesService {
    @Autowired
    private final RetiringCompaniesRepository retiringCompaniesRepository;
    @Autowired
    private CompaniesRepository companiesRepository;

    public CompaniesService(RetiringCompaniesRepository retiringCompaniesRepository) {
        this.retiringCompaniesRepository = retiringCompaniesRepository;
    }

    public List<Company> getAllCompanies() {
        return companiesRepository.findAll();
    }

    public Company getCompanyById(Integer companyId) {
        return companiesRepository.findById(companyId).orElse(null);
    }

    public List<Employee> getCompanyEmployeesById(Integer companyId) {
        return Objects.requireNonNull(companiesRepository.findById(companyId).orElse(null)).getEmployees();
    }

    public List<Company> getCompaniesByPagination(Integer pageIndex, Integer pageSize) {
        return companiesRepository.findAll(PageRequest.of((pageIndex - 1),pageSize)).getContent();
    }

    public Company addCompany(Company company) {
        return companiesRepository.save(company);
    }

    public Company updateCompany(Integer employeeId, Company companyUpdated) {
        List<Company> companies = retiringCompaniesRepository.getCompanies();

        return companies.stream()
                .filter(company -> company.getId().equals(employeeId))
                .findFirst()
                .map(company -> updateCompanyName(company, companyUpdated)).orElse(null);
    }

    private Company updateCompanyName(Company company, Company companyUpdated) {
        if (companyUpdated.getCompanyName() != null) {
            company.setCompanyName(companyUpdated.getCompanyName());
        }
        return company;
    }

    public List<Company> deleteCompany(Integer companyId) {
        List<Company> companies = retiringCompaniesRepository.getCompanies();

        companies.removeIf(company -> company.getId().equals(companyId));
        return companies;
    }
}
