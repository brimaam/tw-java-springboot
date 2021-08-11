package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompaniesService {
    @Autowired
    private final CompaniesRepository companiesRepository;

    public CompaniesService(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    public List<Company> getAllCompanies() {
        return companiesRepository.getCompanies();
    }

    public Company getCompanyById(Integer companyId) {
        return companiesRepository.getCompanies().stream()
                .filter(company -> company.getId().equals(companyId))
                .findFirst()
                .orElse(null);
    }
}
