package io.zipcoder.persistenceapp.Entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DepartmentTest {
    private Department department;
    private int dpt_num;
    private String dpt_name;
    private Employee dpt_manager;

//    @Before
//    void SetUp() {
//        department = new Department(dpt_num, dpt_name, dpt_manager);
//    }

    @Test
    public void createDepartment(){
       dpt_num = 25;
       dpt_name = "ITSM Team";
       dpt_manager = new Employee("A123456", "Jekyll", "Hyde", "Lead Engineering Manager",
               "555-555-5555", "JHyde@test.com");

       department = new Department(dpt_num, dpt_name, dpt_manager);

       assertEquals(dpt_num, department.getDpt_num());
       assertEquals(dpt_name, department.getDpt_name());
       assertEquals(dpt_manager, department.getDpt_manager());
    }

}
