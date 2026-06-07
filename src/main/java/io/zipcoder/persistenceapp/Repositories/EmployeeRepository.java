package io.zipcoder.persistenceapp.Repositories;

import io.zipcoder.persistenceapp.Entities.Department;
import io.zipcoder.persistenceapp.Entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findByManager (Employee manager);

    List<Employee> findByManagerIsNull();

    List<Employee> findByDepartment(Department department);
}
