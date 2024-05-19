package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import static org.junit.jupiter.api.Assertions.*;
class EmployeeTest {


    private Employee employee;

    @BeforeEach
    void init(){
        employee = new Employee("1337", 30000);
    }

    @Test
    @DisplayName("GetID returns ID 1337 when ID is 1337")
    void getIDreturnsID1337(){
        boolean result = employee.getId().equals("1337");
        assertTrue(result);
    }

    @Test
    @DisplayName("GetSalary returns 30000 when salary is 30000")
    void getSalaryReturns30000WhenSalaryIs30000(){
        boolean result = employee.getSalary()==30000;
        assertTrue(result);
    }

    @Test
    @DisplayName("IsPaid returns false if paid not set")
    void isPaidReturnsTrueIfPaidNotSet(){
        assertFalse(employee.isPaid());
    }

    @Test
    @DisplayName("ID is correct after setID")
    void IDIsCorrectAfterSetID (){
        employee.setId("13");
        assertTrue(employee.getId().equals("13"));
    }
    @Test
    @DisplayName("Salary is correct after setSalary")
    void salaryIsCorrectAfterSetSalary (){
        employee.setSalary(333000);
        assertTrue(employee.getSalary()==333000);
    }

    @Test
    @DisplayName("isPaid is true after setPaid to true")
    void isPaidIsTrueAfterSetPaidToTrue (){
        employee.setPaid(true);
        assertTrue(employee.isPaid());
    }

    @Test
    @DisplayName("toString returns appropriate string")
    void toStringReturnsRightString(){

        assertTrue(employee.toString().equals("Employee [id=" + employee.getId() + ", salary=" + employee.getSalary() + "]"));
    }


}