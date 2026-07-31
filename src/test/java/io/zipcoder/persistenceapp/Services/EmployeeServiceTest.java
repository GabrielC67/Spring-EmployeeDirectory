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

import java.text.SimpleDateFormat;
import java.util.*;

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

    Queue<Employee> employeeQueue;


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

        employeeQueue = new LinkedList<>();
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
    public void testUpdateEmployeeToSetManager(){

        when(employeeRepository.findOne(1L)).thenReturn(employee1);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        Employee result = employeeService.updateEmployeeManager(1L, manager);

        assertNotNull(result);
        assertEquals(manager, result.getManager());

    }

    @Test
    public void testUpdateEmployeeFirstName(){
        // Given
        employee2.setFirstName("Jordan");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee2);

        // When
        Employee result = employeeService.updateEmployee(employee2);

        // Then
        assertNotNull(result);
        assertEquals("Jordan", result.getFirstName());
    }

    @Test
    public void testUpdateEmployeeLastName(){
        //Given
        employee3.setLastName("Brown");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee3);

        //When
        Employee result = employeeService.updateEmployee(employee3);

        //Then
        assertNotNull(result);
        assertEquals("Brown", result.getLastName());
    }

    @Test
    public void testUpdateEmployeeNumber(){
        //Given
        employee2.setEmployeeNumber("SE654927");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee2);

        //When
        Employee result = employeeService.updateEmployee(employee2);

        //Then
        assertNotNull(result);
        assertEquals("SE654927", result.getEmployeeNumber());
    }

    @Test
    public void testUpdateEmployeeTitle(){
        //Given
        employee1.setTitle("Chief Medical Officer");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        //When
        Employee result = employeeService.updateEmployee(employee1);

        //Then
        assertEquals("Chief Medical Officer", result.getTitle());
    }

    @Test
    public void testUpdateEmployeePhoneNumber(){
        //Given
        employee1.setPhoneNumber("(321) 654-9870");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        //When
        Employee result = employeeService.updateEmployee(employee1);

        //Then
        assertEquals("(321) 654-9870", result.getPhoneNumber());
    }

    @Test
    public void testUpdateEmployeeEmailAddress(){
        //Given
        employee2.setEmailAddress("gab@test.net");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee2);

        //When
        Employee result = employeeService.updateEmployee(employee2);

        //Then
        assertEquals("gab@test.net", result.getEmailAddress());
    }

    @Test
    public void testUpdateEmployeeHireDate() throws Exception {
        //Given
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date correctedHireDate = sdf.parse("02-01-2023");

        employee3.setHireDate(correctedHireDate);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee3);

        //When
        Employee result = employeeService.updateEmployee(employee3);

        //Then
        assertEquals(correctedHireDate, result.getHireDate());
    }

    @Test
    public void testUpdateEmployeesDepartment(){
        //Given
        Department newDepartment = new Department(5, "testDepartment", null);
        employee1.setDepartment(newDepartment);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        //When
        Employee result = employeeService.updateEmployee(employee1);

        //Then
        assertEquals(newDepartment, result.getDepartment());
    }

    @Test
    public void testDeleteEmployee(){

        employeeService.deleteEmployee(3L);

        //Then
        verify(employeeRepository).delete(3L);
    }

    @Test
    public void testRemoveAllEmployees(){
        employeeService.removeAllEmployees();

        //Then
        verify(employeeRepository).deleteAll();

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

    @Test
    public void testGetAllReports(){
        //Given
        // Tree: manager(4) → employee1(1) → employee2(2)
        // employee1 reports to manager; employee2 reports to employee1
        employee1.setManager(manager);
        employee2.setManager(manager);
        employee3.setManager(employee1);

        List<Employee> directReportsOfManager = new ArrayList<>();
        directReportsOfManager.add(employee1);
        directReportsOfManager.add(employee2);

        List<Employee> directReportsOfEmployee1 = new ArrayList<>();
        directReportsOfEmployee1.add(employee3);

        List<Employee> noReports = new ArrayList<>();

        when(employeeRepository.findOne(4L)).thenReturn(manager);
        when(employeeRepository.findByManager(manager)).thenReturn(directReportsOfManager);
        when(employeeRepository.findByManager(employee1)).thenReturn(directReportsOfEmployee1);
        when(employeeRepository.findByManager(employee2)).thenReturn(noReports);

        //When
        List<Employee> result = employeeService.getAllReports(4L);

        //Then
        assertEquals(3, result.size());
        assertTrue(result.contains(employee1));
        assertTrue(result.contains(employee2));
    }

//
//    @Test
//    public void testRemoveAllUnderManager(){}
}