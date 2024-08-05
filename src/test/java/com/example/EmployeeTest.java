package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void TestGetId(){
        var employee = new Employee("1", 10000);
        assertEquals("1", employee.getId());
    }

    @Test
    void TestGetSalary(){
        var employee = new Employee("1", 10000);
        assertEquals(10000, employee.getSalary());
    }
    @Test
    void TestSetId(){
        var employee = new Employee("1", 10000);
        employee.setId("2");
        assertEquals("2", employee.getId());
    }

    @Test
    void TestSetSalary(){
        var employee = new Employee("1", 10000);
        employee.setSalary(20000);
        assertEquals(20000, employee.getSalary());
    }
}