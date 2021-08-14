package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    private final EmployeesService employeesService;
    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeMapper.toResponse(employeesService.getAllEmployees());
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeResponse findEmployeeById(@PathVariable Integer employeeId) {
        return employeeMapper.toResponse(employeesService.findEmployeeById(employeeId));
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<EmployeeResponse> getEmployeesByPagination(@RequestParam Integer pageIndex,
                                                           @RequestParam Integer pageSize) {
        return employeeMapper.toResponse(employeesService.getEmployeesByPagination(pageIndex, pageSize));
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getAllEmployeesByGender(@RequestParam String gender) {
        return employeeMapper.toResponse(employeesService.getAllEmployeesByGender(gender));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeMapper.toResponse(employeesService.addEmployee(employeeMapper.toEntity(employeeRequest)));
    }

    @PutMapping(path = "/{employeeId}")
    public EmployeeResponse updateEmployee(@PathVariable Integer employeeId,
                                           @RequestBody EmployeeRequest employeeRequest) {
        return employeeMapper.toResponse(employeesService.updateEmployee(employeeId,
                employeeMapper.toEntity(employeeRequest)));
    }

    @DeleteMapping(path = "/{employeeId}")
    private List<EmployeeResponse> deleteEmployee(@PathVariable Integer employeeId) {
        return employeeMapper.toResponse(employeesService.deleteEmployeeRecord(employeeId));
    }
}