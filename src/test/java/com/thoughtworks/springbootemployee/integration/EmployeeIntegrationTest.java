package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeesRepository employeesRepository;

    @Test
    void should_return_all_employees_when_call_get_employees_api() throws Exception{
        //given
        final Employee employee = new Employee(1, "Tom", 20, "male", 9999);
        employeesRepository.save(employee);
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(9999));
    }

    @Test
    void should_create_employee_when_call_create_employee_api() throws Exception {
        //given
        String employee ="{\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Jeff\",\n" +
                "    \"age\": 23,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 3000\n" +
                "}";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON).content(employee))
                .andExpect(status().isCreated());
    }

    @Test
    void should_update_employee_when_call_update_employee_api() throws Exception {
        //given
        Integer employeeId = 1;
        final Employee employee = new Employee(employeeId, "Tom", 20, "male", 9999);
        final Employee savedEmployee = employeesRepository.save(employee);
        String employeeUpdates ="{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Sarah\",\n" +
                "    \"age\": 23,\n" +
                "    \"gender\": \"female\",\n" +
                "    \"salary\": 3000\n" +
                "}";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON).content(employeeUpdates))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sarah"))
                .andExpect(jsonPath("$.gender").value("female"))
                .andExpect(jsonPath("$.salary").value(3000));
    }

    @Test
    void should_delete_employee_when_call_delete_employee_api() throws Exception {
        //given
        Integer employeeId = 1;
        final Employee firstEmployee = new Employee(employeeId, "Tom", 20, "male", 9999);
        final Employee secondEmployee = new Employee(2, "Jane", 23, "female", 9199);
        employeesRepository.save(firstEmployee);
        employeesRepository.save(secondEmployee);


        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{employeeId}", employeeId))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_employee_when_call_get_employee_by_id_api() throws Exception {
        //given
        Integer employeeId = 1;
        final Employee firstEmployee = new Employee(employeeId, "Tom", 20, "male", 9999);
        final Employee secondEmployee = new Employee(2, "Jane", 23, "female", 9199);
        employeesRepository.save(firstEmployee);
        employeesRepository.save(secondEmployee);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(9999));
    }

}
