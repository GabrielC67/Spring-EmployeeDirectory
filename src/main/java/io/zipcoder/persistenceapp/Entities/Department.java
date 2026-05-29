package io.zipcoder.persistenceapp.Entities;

public class Department{

    private Long id;
    private int dpt_num;
    private String dpt_name;
    private Employee dpt_manager;

    public Department(){}

    public Department(Integer dpt_num, String dpt_name, Employee dpt_manager){
        this.dpt_num = dpt_num;
        this.dpt_name = dpt_name;
        this.dpt_manager = dpt_manager;
    }

    public int getDpt_num() {
        return dpt_num;
    }

    public void setDpt_num(Integer dpt_num) {
        this.dpt_num = dpt_num;
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