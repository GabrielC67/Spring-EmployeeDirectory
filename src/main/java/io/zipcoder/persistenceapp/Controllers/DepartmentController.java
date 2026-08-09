package io.zipcoder.persistenceapp.Controllers;

import io.zipcoder.persistenceapp.Entities.Department;
import io.zipcoder.persistenceapp.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // ----- CREATE -----

    @PostMapping("/departments")
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    // ----- READ -----

    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentService.findAllDepartments();
    }

    @GetMapping("/departments/{id}")
    public Department getDepartment(@PathVariable Long id) {
        return departmentService.findDepartment(id);
    }

    // ----- UPDATE -----

    // Handles both "set a new department manager" and "change the department name"
    // (send the full updated Department as JSON)
    @PutMapping("/departments")
    public Department updateDepartment(@RequestBody Department department) {
        return departmentService.updateDepartment(department);
    }

    // ----- DELETE -----

    @DeleteMapping("/departments/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    // Clear all employees out of a department (their department field is set to null)
    @DeleteMapping("/departments/{id}/employees")
    public void removeAllEmployeesFromDepartment(@PathVariable Long id) {
        departmentService.removeAllEmployeesFromDpt(id);
    }

    // ----- MERGE -----

    // Merge department B into department A:
    //   - B's manager is set to report to A's manager
    //   - all of B's employees are moved into department A
    @PutMapping("/departments/merge/{deptA_id}/{deptB_id}")
    public void mergeDepartments(@PathVariable Long deptA_id, @PathVariable Long deptB_id) {
        departmentService.mergeDepartmentManagers(deptA_id, deptB_id);
        departmentService.mergeDepartments(deptA_id, deptB_id);
    }
}