package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    private final List<Employee> employees = new ArrayList<>();
    @Autowired
    private EmployeesService employeesService;

    public EmployeesController(){
        employees.add(new Employee(1, "alice", 20, "female", 2000));
        employees.add(new Employee(2, "bob", 21, "male", 1000));
    }

    public EmployeesController(EmployeesService employeesService){
        this.employeesService = employeesService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeesService.getAllEmployees();
    }


    @GetMapping(path = "/{employeeId}")
    public Employee findEmployeeById(@PathVariable Integer employeeId){
        return employeesService.findEmployeeById(employeeId);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Employee> getEmployeesByPagination(@RequestParam Integer pageIndex, @RequestParam Integer pageSize){
        return employeesService.getEmployeesByPagination(pageIndex,pageSize);
    }

    @GetMapping (params="gender")
    public List<Employee> getAllEmployeesByGender(@RequestParam String gender){
        return employeesService.getAllEmployeesByGender(gender);
    }

    @PostMapping
    public void addEmployee(@RequestBody Employee employee){
        employeesService.addEmployee(employee);
    }

    @PutMapping(path = "/{employeeId}")
    public Employee updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee employeeUpdated){
        return employeesService.updateEmployee(employeeId,employeeUpdated);
    }

    @DeleteMapping(path = "/{employeeId}")
    private List<Employee>  deleteEmployeeRecord(@PathVariable Integer employeeId){
        Iterator<Employee> itr = employees.iterator();
        // remove all even numbers
        while (itr.hasNext()) {
            Employee employee = itr.next();
            if (employee.getId().equals(employeeId)) {
                employees.remove(employee);
            }
        }
        return employees;
    }
}