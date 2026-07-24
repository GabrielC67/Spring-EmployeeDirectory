package io.zipcoder.persistenceapp.Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Department{

    @OneToMany(mappedBy="department")
    private Set<Employee> employees;

    @OneToOne
    @JoinColumn(name="manager_id")
    private Employee dpt_manager;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dpt_num;
    private String dpt_name;

    //Default Constructor
    public Department(){}

    public Department(Integer dpt_num, String dpt_name, Employee dpt_manager){
        this.dpt_num = dpt_num;
        this.dpt_name = dpt_name;
        this.dpt_manager = dpt_manager;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void setDpt_num(Integer dpt_num) {
        this.dpt_num = dpt_num;
    }

    public int getDpt_num() {
        return dpt_num;
    }

    public String getDpt_name() {
        return dpt_name;
    }

    public void setDpt_name(String dpt_name) {
        this.dpt_name = dpt_name;
    }

    public Employee getDpt_manager() {
        return dpt_manager;
    }

    public void setDpt_manager(Employee dpt_manager) {
        this.dpt_manager = dpt_manager;
    }
}