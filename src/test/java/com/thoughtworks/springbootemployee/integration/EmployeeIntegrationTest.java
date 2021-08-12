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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jeff"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(3000));
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jane"))
                .andExpect(jsonPath("$[0].age").value("23"))
                .andExpect(jsonPath("$[0].gender").value("female"))
                .andExpect(jsonPath("$[0].salary").value(9199));;
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

    @Test
    void should_return_employees_by_pagination_when_call_get_employee_by_pagination_api() throws Exception {
        //given
        String pageIndex = "1";
        String pageSize = "3";

        final Employee firstEmployee = new Employee(1, "Tom", 20, "male", 9999);
        final Employee secondEmployee = new Employee(2, "Jane", 23, "female", 9199);
        final Employee thirdEmployee = new Employee(3, "Freddy", 25, "male", 9399);
        final Employee fourthEmployee = new Employee(4, "Mick", 26, "male", 9199);
        final Employee fifthEmployee = new Employee(5, "Sally", 23, "female", 9899);
        employeesRepository.save(firstEmployee);
        employeesRepository.save(secondEmployee);
        employeesRepository.save(thirdEmployee);
        employeesRepository.save(fourthEmployee);
        employeesRepository.save(fifthEmployee);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                .param("pageIndex", pageIndex)
                .param("pageSize",pageSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[1].name").value("Jane"))
                .andExpect(jsonPath("$[2].name").value("Freddy"));
    }

    @Test
    void should_return_employees_by_gender_when_call_get_employee_by_gender_api() throws Exception {
        //given
        String employeeGender = "male";

        final Employee firstEmployee = new Employee(1, "Tom", 20, "male", 9999);
        final Employee secondEmployee = new Employee(2, "Jane", 23, "female", 9199);
        final Employee thirdEmployee = new Employee(3, "Freddy", 25, "male", 9399);
        final Employee fourthEmployee = new Employee(4, "Mick", 26, "male", 9199);
        final Employee fifthEmployee = new Employee(5, "Sally", 23, "female", 9899);
        employeesRepository.save(firstEmployee);
        employeesRepository.save(secondEmployee);
        employeesRepository.save(thirdEmployee);
        employeesRepository.save(fourthEmployee);
        employeesRepository.save(fifthEmployee);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                .param("gender", employeeGender))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[1].name").value("Freddy"))
                .andExpect(jsonPath("$[2].name").value("Mick"));
    }

}
