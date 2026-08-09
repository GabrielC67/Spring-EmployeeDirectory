package io.zipcoder.persistenceapp.Controllers;

import io.zipcoder.persistenceapp.Entities.Department;
import io.zipcoder.persistenceapp.Entities.Employee;
import io.zipcoder.persistenceapp.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ----- CREATE -----

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    // ----- READ -----

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.findEmployee(id);
    }

    // ----- UPDATE -----

    // Update general employee fields (send the full updated Employee as JSON)
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    // Set an employee's manager (send the manager Employee as JSON)
    @PutMapping("/employees/{id}/manager")
    public Employee updateEmployeeManager(@PathVariable Long id, @RequestBody Employee manager) {
        return employeeService.updateEmployeeManager(id, manager);
    }

    // ----- ATTRIBUTE QUERIES -----

    @GetMapping("/employees/{id}/title")
    public String getEmployeeTitle(@PathVariable Long id) {
        return employeeService.findEmployee(id).getTitle();
    }

    @GetMapping("/employees/{id}/department")
    public Department getEmployeeDepartment(@PathVariable Long id) {
        return employeeService.findEmployee(id).getDepartment();
    }

    // ----- HIERARCHY QUERIES -----

    // Direct reports only
    @GetMapping("/employees/{id}/reports")
    public List<Employee> getEmployeesUnderManager(@PathVariable Long id) {
        return employeeService.getEmployeesUnderManager(id);
    }

    // Direct + indirect reports (the full subtree below this manager)
    @GetMapping("/employees/{id}/all-reports")
    public List<Employee> getAllReports(@PathVariable Long id) {
        return employeeService.getAllReports(id);
    }

    // Upward chain: this employee's manager, their manager, etc.
    @GetMapping("/employees/{id}/hierarchy")
    public List<Employee> getReportingHierarchy(@PathVariable Long id) {
        return employeeService.getReportingHierarchy(id);
    }

    // Employees with no assigned manager
    @GetMapping("/employees/no-manager")
    public List<Employee> getEmployeesWithoutManager() {
        return employeeService.getEmployeesWithoutManager();
    }

    // All employees belonging to a given department
    @GetMapping("/employees/by-department/{departmentId}")
    public List<Employee> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return employeeService.getEmployeesByDepartment(departmentId);
    }

    // ----- DELETE -----

    // Remove a single employee
    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    // Remove every employee
    @DeleteMapping("/employees")
    public void removeAllEmployees() {
        employeeService.removeAllEmployees();
    }

    // Remove all reports under a manager (direct AND indirect)
    @DeleteMapping("/employees/{id}/all-reports")
    public void removeAllUnderManager(@PathVariable Long id) {
        employeeService.removeAllUnderManager(id);
    }

    // Remove a manager's direct reports; their reports are promoted up to this manager
    @DeleteMapping("/employees/{id}/direct-reports")
    public void removeDirectReports(@PathVariable Long id) {
        employeeService.removeDirectReports(id);
    }
}