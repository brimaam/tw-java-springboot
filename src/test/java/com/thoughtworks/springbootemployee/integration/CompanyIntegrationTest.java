package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompaniesRepository;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompaniesRepository companiesRepository;
    @Autowired
    private EmployeesRepository employeesRepository;
    @AfterEach
    void cleanDate(){
        companiesRepository.deleteAll();
    }

    @Test
    void should_return_all_companies_when_call_get_companies_api() throws Exception {

        final Company twitterCompany = new Company("Twitter");
        final Company jypCompany = new Company("JYP");
        companiesRepository.saveAll(Lists.list(twitterCompany,jypCompany));

        Integer companyId = twitterCompany.getId();

        employeesRepository.save(new Employee( "gail", 22, "female", 2000,companyId));
        employeesRepository.save(new Employee( "franco", 21, "male", 1000,companyId));

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("Twitter"))
                .andExpect(jsonPath("$[1].companyName").value("JYP"));
    }

    @Test
    void should_create_company_when_call_create_company_api() throws Exception {
        //given
        String employee ="{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"Jollibee\"\n" +
                "}";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyName").value("Jollibee"));
    }

    @Test
    void should_update_company_when_call_update_company_api() throws Exception {
        //given

        final Company appleCompany = new Company("Apple");
        companiesRepository.save(appleCompany);

        Integer companyId = appleCompany.getId();

        employeesRepository.save(new Employee( "gail", 22, "female", 2000,companyId));
        employeesRepository.save(new Employee( "franco", 21, "male", 1000,companyId));

        String companyUpdates ="{\n" +
                "    \"companyName\": \"Mac\"\n" +
                "}";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/{companyId}", companyId)
                .contentType(MediaType.APPLICATION_JSON).content(companyUpdates))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Mac"));
    }

    @Test
    void should_delete_company_when_call_delete_company_api() throws Exception {
        //given
        Integer companyId = 1;
        List<Employee> twitterEmployees = new ArrayList<>();
        twitterEmployees.add(new Employee("gail", 22, "female", 2000,1));
        twitterEmployees.add(new Employee("franco", 21, "male", 1000,1));

        List<Employee> mcdoEmployees = new ArrayList<>();
        mcdoEmployees.add(new Employee("John", 22, "male", 2800,2));
        mcdoEmployees.add(new Employee("Max", 25, "female", 1800,2));

        Company twitterCompany = new Company(1,"Twitter",twitterEmployees);
        Company mcdoCompany = new Company(2,"Mcdonalds",mcdoEmployees);
        companiesRepository.saveAll(Lists.list(twitterCompany,mcdoCompany));


        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{companyId}", companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("Mcdonalds"));
    }

    @Test
    void should_return_company_when_call_get_company_by_id_api() throws Exception {
        //given
        Company jypCompany = new Company("JYP");
        companiesRepository.save(jypCompany);

        Company twitterCompany = new Company("Twitter");
        companiesRepository.save(twitterCompany);

        Integer companyId = twitterCompany.getId();
        employeesRepository.save(new Employee( "gail", 22, "female", 2000,companyId));
        employeesRepository.save(new Employee( "franco", 21, "male", 1000,companyId));


        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}", companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Twitter"));
    }

    @Test
    void should_return_employee_when_call_get_employee_by_company_id_api() throws Exception {
        //given

        Company twitterCompany = new Company("Twitter");
        companiesRepository.save(twitterCompany);

        Integer companyId = twitterCompany.getId();
        employeesRepository.save(new Employee( "gail", 22, "female", 2000,companyId));
        employeesRepository.save(new Employee( "franco", 21, "male", 1000,companyId));

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}/employees", companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("gail"))
                .andExpect(jsonPath("$[1].name").value("franco"));
    }

    @Test
    void should_return_employees_by_pagination_when_call_get_employee_by_pagination_api() throws Exception {
        //given
        String pageIndex = "1";
        String pageSize = "3";

        Company twitterCompany = new Company("Twitter");
        Company jypCompany = new Company("JYP");
        Company marvelCompany = new Company("Marvel");
        Company universalCompany = new Company("Universal");
        Company amdCompany = new Company("AMD");
        companiesRepository.saveAll(Lists.list(twitterCompany,jypCompany,marvelCompany,universalCompany,amdCompany));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies")
                .param("pageIndex", pageIndex)
                .param("pageSize",pageSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("Twitter"))
                .andExpect(jsonPath("$[1].companyName").value("JYP"))
                .andExpect(jsonPath("$[2].companyName").value("Marvel"));
    }
}
