package io.zipcoder.persistenceapp.Services;

import io.zipcoder.persistenceapp.Entities.Department;
import io.zipcoder.persistenceapp.Entities.Employee;
import io.zipcoder.persistenceapp.Repositories.DepartmentRepository;
import io.zipcoder.persistenceapp.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository){
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployee(long id) {
        return employeeRepository.findOne(id);
    }


    public List<Employee> findAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(long id) {
        employeeRepository.delete(id);
    }

    public Employee updateEmployeeManager(long id, Employee manager) {
        Employee employee = employeeRepository.findOne(id);
        employee.setManager(manager);
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesUnderManager(Long managerId) {
        Employee manager = employeeRepository.findOne(managerId);
        return employeeRepository.findByManager(manager);
    }


    public List<Employee> getEmployeesWithoutManager() {
        return employeeRepository.findByManagerIsNull();
    }

    public List<Employee> getReportingHierarchy(Long employeeId) {
        List<Employee> hierarchy = new ArrayList<>();
        Set<Long> visited = new HashSet<>();

        Employee employee = employeeRepository.findOne(employeeId);

        while (employee.getManager() != null) {
            Employee manager = employee.getManager();
            Long managerId = manager.getId();

            if (!visited.add(managerId)){
                break; // cycle detected!
            }

            hierarchy.add(manager);
            employee = manager;
        }

        return hierarchy;
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        Department department = departmentRepository.findOne(departmentId);
        return employeeRepository.findByDepartment(department);
    }
}
