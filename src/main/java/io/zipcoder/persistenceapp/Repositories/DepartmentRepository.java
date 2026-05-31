package io.zipcoder.persistenceapp.Repositories;

import io.zipcoder.persistenceapp.Entities.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
