package io.zipcoder.persistenceapp.Services;

import io.zipcoder.persistenceapp.Entities.Employee;
import io.zipcoder.persistenceapp.Repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    Employee employee1;
    Employee employee2;
    Employee employee3;
    Employee manager;
    List<Employee> employeeList1;
    List<Employee> employeeList2;

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
        employeeList1 = new ArrayList<>();
        employeeList2 = new ArrayList<>();

        employeeList1.add(employee1);
        employeeList1.add(employee2);
        employeeList1.add(manager);

        employeeList2.add(employee3);

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
}