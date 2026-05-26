package io.zipcoder.persistenceapp.Entities;


public class Employee{

    private String employeeNumber;
    private String firstName;
    private String lastName;
    private String title;
    private String phoneNumber;
    private String emailAddress;

    public Employee(String employeeNumber, String firstName, String lastName, String title, String phoneNumber, String emailAddress) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public Employee() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}