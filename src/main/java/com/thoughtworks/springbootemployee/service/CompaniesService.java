package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompaniesRepository;
import com.thoughtworks.springbootemployee.repository.RetiringCompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return retiringCompaniesRepository.getCompanies();
    }

    public Company getCompanyById(Integer companyId) {
        return retiringCompaniesRepository.getCompanies().stream()
                .filter(company -> company.getId().equals(companyId))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getCompanyEmployeesById(Integer companyId) {
        Company company =  companiesRepository.findById(companyId).orElse(null);
        return company.getEmployees();
    }

    public List<Company> getCompaniesByPagination(Integer pageIndex, Integer pageSize) {
        return retiringCompaniesRepository.getCompanies().stream()
                .skip((long) (pageIndex - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Company> addCompany(Company company) {
        List<Company> companies = retiringCompaniesRepository.getCompanies();
        Company companyToBeAdded = new Company(companies.size() + 1,
                company.getCompanyName(), company.getEmployees());

        companies.add(companyToBeAdded);

        return companies;
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
