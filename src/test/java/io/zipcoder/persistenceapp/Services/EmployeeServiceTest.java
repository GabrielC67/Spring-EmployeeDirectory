package io.zipcoder.persistenceapp.Services;

import io.zipcoder.persistenceapp.Entities.Department;
import io.zipcoder.persistenceapp.Entities.Employee;
import io.zipcoder.persistenceapp.Repositories.DepartmentRepository;
import io.zipcoder.persistenceapp.Repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    Employee employee1;
    Employee employee2;
    Employee employee3;
    Employee manager;
    Employee aboveManager;
    List<Employee> employeeList1;
    List<Employee> employeeList2;
    List<Employee> employeeHierarchy;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        employee1 = new Employee("M654889", "Rebekah", "Hartzel", "Medical Assistant",
                "888-999-0000", "rHMed@test.net");
        employee2 = new Employee("T654896", "Gabe", "Cruz", "Senior Full-Stack Developer",
                "555-555-5555", "gCTech@test.net");
        employee3 = new Employee("K657892", "Tere", "Arroyo", "Restaurant Manager",
                "333-333-3333", "tARest@test.net");
        manager = new Employee("MD654897", "Jack", "Alltrade", "Development Manager",
                "789-456-0152", "jATrades@test.net");
        aboveManager = new Employee("TOP001", "Sarah", "Connor",
                "VP Engineering", "111-111-1111", "sconnor@test.net");

        employee1.setId(1L);
        employee2.setId(2L);
        employee3.setId(3L);
        manager.setId(4L);
        aboveManager.setId(5L);

        employeeList1 = new ArrayList<>();
        employeeList2 = new ArrayList<>();
        employeeHierarchy = new ArrayList<>();

        employeeList1.add(employee1);
        employeeList1.add(employee2);
        employeeList1.add(manager);

        employee1.setManager(manager);
        employee2.setManager(manager);
        manager.setManager(aboveManager);

        employeeList2.add(employee3);

        employeeHierarchy.add(manager);
        employeeHierarchy.add(aboveManager);
    }

    @Test
    public void testCreateEmployee(){
        //Given
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        //When
        Employee result = employeeService.createEmployee(employee1);

        assertNotNull(employee1);
        assertEquals("Rebekah", result.getFirstName());
    }

    @Test
    public void testFindEmployee(){
        //Given
        when(employeeRepository.findOne(anyLong())).thenReturn(employee1);

        //When
        Employee result = employeeService.findEmployee(1L);

        //Then
        assertNotNull(result);
        assertEquals("Rebekah", result.getFirstName());
    }

    @Test
    public void testFindAllEmployees(){
        //Given
        when(employeeRepository.findAll()).thenReturn((Iterable<Employee>) employeeList1);

        //When
        List<Employee> result = employeeService.findAllEmployees();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testUpdateEmployee(){
        // Given
        employee2.setFirstName("Jackson");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee2);

        // When
        Employee result = employeeService.updateEmployee(employee2);

        // Then
        assertNotNull(result);
        assertEquals("Jackson", result.getFirstName()); // ← Check result!
    }

    @Test
    public void testDeleteEmployee(){

        employeeService.deleteEmployee(3L);

        //Then
        verify(employeeRepository).delete(3L);
    }

    @Test
    public void testUpdateEmployeesManager(){

        when(employeeRepository.findOne(1L)).thenReturn(employee1);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        Employee result = employeeService.updateEmployeeManager(1L, manager);

        assertNotNull(result);
        assertEquals(manager, result.getManager());

    }

    @Test
    public void testGetEmployeesUnderManager(){
        //Given
        when(employeeRepository.findOne(anyLong())).thenReturn(manager);
        when(employeeRepository.findByManager(manager)).thenReturn(employeeList1);

        //When
        List<Employee> result = employeeService.getEmployeesUnderManager(4L);

        //Then
        assertNotNull(employeeList1);
        System.out.println(result);
        assertEquals(employeeList1, result);
    }

    @Test
    public void testGetEmployeesWhenManagerIsNull(){
        //Given
        when(employeeRepository.findOne(anyLong())).thenReturn(null);
        when(employeeRepository.findByManagerIsNull()).thenReturn(employeeList2);

        //When
        List<Employee> result = employeeService.getEmployeesWithoutManager();

        //Then
        assertNotNull(employeeList2);
        assertEquals(employeeList2, result);
    }

    @Test
    public void testEmployeeHierarchy(){
        //Given
        when(employeeRepository.findOne(anyLong())).thenReturn(employee1);
        when(employeeRepository.findByManager(manager)).thenReturn(employeeList1);
        when(employeeRepository.findByManager(aboveManager)).thenReturn(employeeHierarchy);

        //When
        List<Employee> result = employeeService.getReportingHierarchy(1L);

        //Then
        assertNotNull(employeeHierarchy);
        assertEquals(employeeHierarchy, result);
    }

    @Test
    public void testFindByDepartment(){
        //Given
        Department department = new Department(1, "Engineering", manager);

        when(departmentRepository.findOne(anyLong())).thenReturn(department);
        when(employeeRepository.findByDepartment(department)).thenReturn(employeeList1);

        //When
        List<Employee> result = employeeService.getEmployeesByDepartment(1L);

        //Then
        assertNotNull(result);
        assertEquals(employeeList1, result);
    }
}