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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DepartmentService departmentService;

    List<Department> departmentList;

    Employee dpt_01Manager;
    Employee dpt_02Manager;
    Employee dpt_03Manager;

    Department dpt_01;
    Department dpt_02;
    Department dpt_03;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        dpt_01Manager = new Employee("H123654", "Adam", "Cole",
                "Director of Human Resources", "(555) 789-3214",
                "ACole@test.com");
        dpt_01 = new Department(1, "Human Resources", dpt_01Manager);

        dpt_02Manager = new Employee("E456789", "Roderick", "Strong",
                "Chief Executive Officer", "(650) 498-7321", "HStrong@test.net");
        dpt_02 = new Department(2, "Executives", dpt_02Manager);

        dpt_03Manager = new Employee("S15678", "Jacob", "Fatu", "Sales Manager",
                "(450) 225-8956", "JFatu@test.com");
        dpt_03 = new Department(3, "Sales", dpt_03Manager);

        departmentList = new ArrayList<Department>(){};

        departmentList.add(dpt_01);
        departmentList.add(dpt_02);
        departmentList.add(dpt_03);

    }

    @Test
    public void createDepartmentTest(){
        //Given
        when(departmentRepository.save(any(Department.class))).thenReturn(dpt_01);

        //When
        Department result = departmentService.createDepartment(dpt_01);

        //Then
        assertNotNull(result);
        assertEquals("Human Resources", result.getDpt_name() );
    }

    @Test
    public void testUpdateDepartmentNumber(){

    }

    @Test
    public void testUpdateDepartmentName(){
        //Given
        dpt_02.setDpt_name("Leadership");
        when(departmentRepository.save(any(Department.class))).thenReturn(dpt_02);

        //When
        Department result = departmentService.updateDepartment(dpt_02);

        //Then
        assertNotNull(result);
        assertEquals("Leadership", result.getDpt_name());
    }

    @Test
    public void testUpdateDepartmentManager() throws Exception{
        //Given
        dpt_01Manager.setEmployeeNumber("H986459");
        dpt_01Manager.setFirstName("Roger");
        dpt_01Manager.setLastName("Federer");
        dpt_01Manager.setPhoneNumber("(123) 654-9874");
        dpt_01Manager.setEmailAddress("Rf@test.net");

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date newManagerHireDate = sdf.parse("08-01-2019");
        dpt_01Manager.setHireDate(newManagerHireDate);

        dpt_01.setDpt_manager(dpt_01Manager);

        when(departmentRepository.save(any(Department.class))).thenReturn(dpt_01);

        //Then
        Department result = departmentService.updateDepartment(dpt_01);

        //When
        assertEquals(dpt_01, result);
    }

    @Test
    public void findDepartmentTest(){
        //Given
        when(departmentRepository.findOne(any(Long.class))).thenReturn(dpt_03);

        //When
        Department result = departmentService.findDepartment(3L);

        //Then
        assertEquals(3, result.getDpt_num());
    }

    @Test
    public void findAllDepartmentsTest(){
        //Given
        when(departmentRepository.findAll()).thenReturn(departmentList);

        //When
        List<Department> resultList = departmentService.findAllDepartments();

        //Then
        assertNotNull(resultList);
        assertEquals(3, resultList.size());
    }

    @Test
    public void deleteDepartmentTest(){
        //Given
          departmentService.deleteDepartment(2L);

        //Then
        verify(departmentRepository).delete(2L);
    }
}
