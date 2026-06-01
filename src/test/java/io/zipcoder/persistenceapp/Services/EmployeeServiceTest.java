package io.zipcoder.persistenceapp.Services;

import io.zipcoder.persistenceapp.Entities.Employee;
import io.zipcoder.persistenceapp.Repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    String employeeNumber;
    String firstName;
    String lastName;
    String title;
    String phoneNumber;
    String emailAddress;
    Date hireDate;
    Employee employee1;
    Employee employee2;
    Employee employee3;
    List<Employee> employeeList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        employee1 = new Employee("M654889", "Rebekah", "Hartzel", "Medical Assistant",
                "888-999-0000", "rHMed@test.net");
        employee2 = new Employee("T654896", "Gabe", "Cruz", "Senior Full-Stack Developer",
                "555-555-5555", "gCTech@test.net");
        employee3 = new Employee("K657892", "Tere", "Arroyo", "Restaurant Manager",
                "333-333-3333", "tARest@test.net");
        employeeList = new ArrayList<>();

        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);

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
        when(employeeRepository.findAll()).thenReturn((Iterable<Employee>) employeeList);

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
}