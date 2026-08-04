package io.zipcoder.persistenceapp.Services;

import io.zipcoder.persistenceapp.Entities.Department;
import io.zipcoder.persistenceapp.Entities.Employee;
import io.zipcoder.persistenceapp.Repositories.DepartmentRepository;
import io.zipcoder.persistenceapp.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department findDepartment(Long id) {
        return departmentRepository.findOne(id);

    }

    public List<Department> findAllDepartments() {
        List<Department> departments = new ArrayList<>();
        departmentRepository.findAll().forEach(departments::add);
        return departments;
    }

    public void deleteDepartment(Long id) {
        departmentRepository.delete(id);
    }


    public void removeAllEmployeesFromDpt(Long departmentId) {
        Department department = departmentRepository.findOne(departmentId);
        Set<Employee> setOfEmployees = department.getEmployees();

        for (Employee employee : setOfEmployees) {
            employee.setDepartment(null);
            employeeRepository.save(employee);
        }
    }

    public void mergeDepartmentManagers(Long dptA_id, Long dptB_id) {
        Department deptA = departmentRepository.findOne(dptA_id);
        Department deptB = departmentRepository.findOne(dptB_id);

        Employee deptA_manager = deptA.getDpt_manager();
        Employee deptB_manager = deptB.getDpt_manager();

        deptB_manager.setManager(deptA_manager);
    }

    public void mergeDepartments(Long dptA_id, Long dptB_id) {
        Department deptA = departmentRepository.findOne(dptA_id);
        Department deptB = departmentRepository.findOne(dptB_id);

        for (Employee employee : deptB.getEmployees()) {
            employee.setDepartment(deptA);
            employeeRepository.save(employee);
        }
    }
}
