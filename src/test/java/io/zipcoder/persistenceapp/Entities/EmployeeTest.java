package io.zipcoder.persistenceapp.Entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class EmployeeTest {
    String employeenNum = null;
    String firstName = null;
    String lastName = null;
    String title = null;
    String phoneNumber = null;
    String emailAddress = null;
    Date hireDate = null;
    String manager = null;
    Department dpt_num = null;

    @Before
    public void setup(){

    }

    @Test
    public void testCreateEmployee(){

        firstName = "Gabe";
        lastName = "Cruz";
        title = "Associate Full Stack Developer";

        Employee employee = new Employee(employeenNum, firstName, lastName, title, phoneNumber, emailAddress);

        assertEquals("Gabe", employee.getFirstName());
        assertEquals("Cruz", employee.getLastName());
        assertEquals("Associate Full Stack Developer", employee.getTitle());
    }

    @Test
    public void testCreateEmployeeWithContactInformation(){

        employeenNum = "A126456";
        firstName = "Gabe";
        lastName = "Cruz";
        title = "Associate Full Stack Developer";
        phoneNumber = "555-555-5555";
        employeenNum = "gctest@develop.net";

        Employee employee = new Employee(employeenNum, firstName, lastName, title,
                phoneNumber, emailAddress);

        assertEquals(employeenNum, employee.getEmployeeNumber());
        assertEquals(phoneNumber, employee.getPhoneNumber());
        assertEquals(emailAddress, employee.getEmailAddress());
    }


}