package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    Employee employee = new Employee("001",40000);

    @Test
    void getIdShouldReturnCorrectValue() {
        assertEquals("001", employee.getId());
    }

    @Test
    void setIdShouldSetCorrectValue() {
        employee.setId("002");
        assertEquals("002",employee.getId());
    }

    @Test
    void getSalaryShouldReturnCorrectValue() {
        assertEquals(40000, employee.getSalary());
    }

    @Test
    void setSalaryShouldSetCorrectValue() {
        employee.setSalary(45000);
        assertEquals(45000, employee.getSalary());
    }

    @Test
    void isPaidShouldReturnFalseAsDefault() {
        assertFalse(employee.isPaid());
    }

    @Test
    void setPaidShouldSetValueToTrue() {
        employee.setPaid(true);
        assertTrue(employee.isPaid());
    }

    @Test
    void testToStringReturnsCorrectString() {
        assertEquals("Employee [id=001, salary=40000.0]", employee.toString());
    }
}