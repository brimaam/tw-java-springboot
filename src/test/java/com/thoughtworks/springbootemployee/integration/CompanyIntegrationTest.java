package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompaniesRepository;
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

    @Test
    void should_return_all_companies_when_call_get_companies_api() throws Exception {
        //given
        List<Employee> appleEmployees = new ArrayList<>();
        appleEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        appleEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        List<Employee> jypEmployees = new ArrayList<>();
        jypEmployees.add(new Employee(1, "John", 22, "male", 2800));
        jypEmployees.add(new Employee(2, "Max", 25, "female", 1800));

        final Company appleCompany = new Company(1,"Apple",appleEmployees);
        final Company jypCompany = new Company(2,"JYP",jypEmployees);
        companiesRepository.save(appleCompany);
        companiesRepository.save(jypCompany);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("Apple"))
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
        Integer companyId = 1;

        List<Employee> appleEmployees = new ArrayList<>();
        appleEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        appleEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        final Company appleCompany = new Company(companyId,"Apple",appleEmployees);
        companiesRepository.save(appleCompany);

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
        Integer companyId = 2;
        List<Employee> twitterEmployees = new ArrayList<>();
        twitterEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        twitterEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        List<Employee> mcdoEmployees = new ArrayList<>();
        mcdoEmployees.add(new Employee(1, "John", 22, "male", 2800));
        mcdoEmployees.add(new Employee(2, "Max", 25, "female", 1800));

        Company twitterCompany = new Company(1,"Twitter",twitterEmployees);
        Company mcdoCompany = new Company(2,"Mcdonalds",mcdoEmployees);
        companiesRepository.save(twitterCompany);
        companiesRepository.save(mcdoCompany);


        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{companyId}", companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyName").value("Twitter"));
    }

    @Test
    void should_return_company_when_call_get_company_by_id_api() throws Exception {
        //given
        Integer companyId = 1;
        List<Employee> twitterEmployees = new ArrayList<>();
        twitterEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        twitterEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        List<Employee> jypEmployees = new ArrayList<>();
        jypEmployees.add(new Employee(1, "John", 22, "male", 2800));
        jypEmployees.add(new Employee(2, "Max", 25, "female", 1800));

        Company twitterCompany = new Company(1,"Twitter",twitterEmployees);
        Company jypCompany = new Company(2,"JYP",jypEmployees);
        companiesRepository.save(twitterCompany);
        companiesRepository.save(jypCompany);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}", companyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Twitter"));
    }

    @Test
    void should_return_employee_when_call_get_employee_by_company_id_api() throws Exception {
        //given
        Integer companyId = 2;
        List<Employee> twitterEmployees = new ArrayList<>();
        twitterEmployees.add(new Employee(1, "gail", 22, "female", 2000));
        twitterEmployees.add(new Employee(2, "franco", 21, "male", 1000));

        List<Employee> jypEmployees = new ArrayList<>();
        jypEmployees.add(new Employee(1, "John", 22, "male", 2800));
        jypEmployees.add(new Employee(2, "Max", 25, "female", 1800));

        Company twitterCompany = new Company(1,"Twitter",twitterEmployees);
        Company jypCompany = new Company(2,"JYP",jypEmployees);
        companiesRepository.save(twitterCompany);
        companiesRepository.save(jypCompany);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}/employees", companyId))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$[0].name").value("gail"))
//                .andExpect(jsonPath("$[1].name").value("franco"));
    }
}
