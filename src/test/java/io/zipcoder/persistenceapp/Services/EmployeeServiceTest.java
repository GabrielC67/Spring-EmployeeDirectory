package io.zipcoder.persistenceapp.Services;

import io.zipcoder.persistenceapp.Entities.Employee;
import io.zipcoder.persistenceapp.Repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

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
    Employee employee;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        employeeNumber= "M654889";
        firstName = "Rebekah";
        lastName = "Hartzel";
        title = "Medical Assistant";
        phoneNumber = "888-999-0000";
        emailAddress = "rHMed@test.net";
        hireDate = new Date();
        employee = new Employee(employeeNumber, firstName, lastName, title, phoneNumber,
                emailAddress);
    }

    @Test
    public void testCreateEmployee(){
        //Given
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        //When
        Employee result = employeeService.createEmployee(employee);

        assertNotNull(employee);
        assertEquals("Rebekah", result.getFirstName());
    }
}