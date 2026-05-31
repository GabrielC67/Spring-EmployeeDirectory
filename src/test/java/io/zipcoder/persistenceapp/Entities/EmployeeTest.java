package io.zipcoder.persistenceapp.Entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class EmployeeTest {
    Employee employee;
    String employeenNum = null;
    String firstName = null;
    String lastName = null;
    String title = null;
    String phoneNumber = null;
    String emailAddress = null;
    Date hireDate = null;
    Employee manager = null;

    @Before
    public void setup(){
        employeenNum = "A126456";
        firstName = "Gabe";
        lastName = "Cruz";
        title = "Associate Full Stack Developer";
        phoneNumber = "555-555-5555";
        emailAddress = "gctest@develop.net";
        employee = new Employee(employeenNum, firstName, lastName, title, phoneNumber, emailAddress);
    }

    @Test
    public void testCreateEmployee(){

        assertEquals("Gabe", employee.getFirstName());
        assertEquals("Cruz", employee.getLastName());
        assertEquals("Associate Full Stack Developer", employee.getTitle());
    }

    @Test
    public void testEmployeeContactInformation(){
        assertEquals(employeenNum, employee.getEmployeeNumber());
        assertEquals(phoneNumber, employee.getPhoneNumber());
        assertEquals(emailAddress, employee.getEmailAddress());
    }

    @Test
    public void testSetDptmanager(){

        Employee manager = new Employee("A123456", "Maxwell", "Newman",
                "Lead Software Development Manager", "888-888-8888", "mnewman@test.com");

        employee.setDpt_manager(manager);

        assertEquals(manager, employee.getDpt_manager());
    }

    @Test
    public void testHireDate() {

        hireDate = new Date();
        employee.setHireDate(hireDate);

        assertEquals(hireDate, employee.getHireDate());
    }

    @Test
    public void testSetDepartment(){

        Department department = new Department(101, "Engineering", manager);

        employee.setDepartment(department);

        assertEquals(department, employee.getDepartment());

    }

}