package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompaniesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CompaniesServiceTest {
    @InjectMocks
    private CompaniesService companiesService;
    @Mock
    private CompaniesRepository companiesRepository;

    @Test
    public void should_return_all_companies_when_getAllCompanies_given_all_companies() {
        //given
        List<Company> companies = new ArrayList<>();
        List<Employee> morningEmployees = new ArrayList<>();
        morningEmployees.add(new Employee(1, "allie", 22, "female", 2000));
        morningEmployees.add(new Employee(2, "diego", 21, "male", 3000));

        List<Employee> nightEmployees = new ArrayList<>();
        nightEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        nightEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        companies.add(new Company(1,"Google",morningEmployees));
        companies.add(new Company(2,"Twitter",nightEmployees));

        given(companiesRepository.getCompanies()).willReturn(companies);

        //when
        List<Company> actualCompanies = companiesService.getAllCompanies();

        //then
        assertIterableEquals(companies, actualCompanies);
    }

    @Test
    public void should_return_company_when_getCompanyById_given_companyId() {
        //given
        Integer companyId = 1;
        List<Company> companies = new ArrayList<>();
        List<Employee> morningEmployees = new ArrayList<>();
        morningEmployees.add(new Employee(1, "allie", 22, "female", 2000));
        morningEmployees.add(new Employee(2, "diego", 21, "male", 3000));

        List<Employee> nightEmployees = new ArrayList<>();
        nightEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        nightEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        companies.add(new Company(1,"Google",morningEmployees));
        companies.add(new Company(2,"Twitter",nightEmployees));

        given(companiesRepository.getCompanies()).willReturn(companies);

        //when
        Company actualCompany = companiesService.getCompanyById(companyId);

        //then
        assertEquals(companyId, actualCompany.getId());
        assertNotNull(actualCompany);
    }

    @Test
    public void should_return_company_employees_when_getCompanyEmployeesById_given_companyId_and_all_employees() {
        //given
        Integer companyId = 1;
        List<Company> companies = new ArrayList<>();
        List<Employee> morningEmployees = new ArrayList<>();
        morningEmployees.add(new Employee(1, "allie", 22, "female", 2000));
        morningEmployees.add(new Employee(2, "diego", 21, "male", 3000));

        List<Employee> nightEmployees = new ArrayList<>();
        nightEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        nightEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        companies.add(new Company(1,"Google",morningEmployees));
        companies.add(new Company(2,"Twitter",nightEmployees));
        companies.add(new Company(3,"Facebook",nightEmployees));

        given(companiesRepository.getCompanies()).willReturn(companies);

        //when
        List <Employee> actualCompanyEmployees = companiesService.getCompanyEmployeesById(companyId);

        //then
        assertEquals(morningEmployees, actualCompanyEmployees);
        assertNotNull(actualCompanyEmployees);
    }

    @Test
    public void should_return_companies_with_page_index_and_size_when_getCompaniesByPagination_given_page_index_and_size() {
        //given
        Integer pageIndex = 1;
        Integer pageSize = 5;
        List<Company> companies = new ArrayList<>();
        List<Employee> morningEmployees = new ArrayList<>();
        morningEmployees.add(new Employee(1, "allie", 22, "female", 2000));
        morningEmployees.add(new Employee(2, "diego", 21, "male", 3000));

        List<Employee> nightEmployees = new ArrayList<>();
        nightEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        nightEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        companies.add(new Company(1,"Google",morningEmployees));
        companies.add(new Company(2,"Twitter",nightEmployees));
        companies.add(new Company(3,"Facebook",nightEmployees));

        given(companiesRepository.getCompanies()).willReturn(companies);

        //when
        List <Company> actualCompanies = companiesService.getCompaniesByPagination(pageIndex,pageSize);

        //then
        assertIterableEquals(companies, actualCompanies);
    }

    @Test
    public void should_add_a_company_to_companies_when_addCompany_given_a_company() {
        //given
        List<Company> companies = new ArrayList<>();
        List<Employee> morningEmployees = new ArrayList<>();
        morningEmployees.add(new Employee(1, "allie", 22, "female", 2000));
        morningEmployees.add(new Employee(2, "diego", 21, "male", 3000));

        List<Employee> nightEmployees = new ArrayList<>();
        nightEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        nightEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        companies.add(new Company(1,"Google",morningEmployees));
        companies.add(new Company(2,"Twitter",nightEmployees));
        companies.add(new Company(3,"Facebook",nightEmployees));

        Integer generatedId = companiesRepository.getCompanies().size() + 1;
        Company company = new Company(generatedId,"Jollibee", nightEmployees);

        given(companiesRepository.getCompanies()).willReturn(companies);

        //when
        List <Company> companiesWithAddedCompany = companiesService.addCompany(company);

        //then
        assertIterableEquals(companies, companiesWithAddedCompany);
        assertTrue(companies.stream().anyMatch(comp -> comp.getId().equals(company.getId())));
    }
    @Test
    void should_update_a_company_when_updateCompany_given_a_companyId_and_a_company_update() {
        //given
        Integer companyId = 1;
        Company companyUpdate = new Company();
        companyUpdate.setCompanyName("Tesla");

        List<Company> companies = new ArrayList<>();
        List<Employee> morningEmployees = new ArrayList<>();
        morningEmployees.add(new Employee(1, "allie", 22, "female", 2000));
        morningEmployees.add(new Employee(2, "diego", 21, "male", 3000));

        List<Employee> nightEmployees = new ArrayList<>();
        nightEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        nightEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        companies.add(new Company(1,"Google",morningEmployees));
        companies.add(new Company(2,"Twitter",nightEmployees));
        companies.add(new Company(3,"Facebook",nightEmployees));
        given(companiesRepository.getCompanies()).willReturn(companies);

        //when
        companiesService.updateCompany(companyId,companyUpdate);

        //then
        assertEquals(companiesService.getCompanyById(companyId).getCompanyName(), companyUpdate.getCompanyName());
    }

}
