package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {


    Employee employee = new Employee("1",11500.00);

    @Test
    void gettingEmployeeId() {
        String actual = employee.getId();
        assertEquals("1", actual);
    }

    @Test
    void settingEmployeeId() {
        employee.setId("2");
        String actual = employee.getId();
        assertEquals("2",actual);
    }

    @Test
    void gettingEmployeeSalary() {
        double actual = employee.getSalary();
        assertEquals(11500.00,actual);
    }

    @Test
    void settingEmployeeSalary() {
        employee.setSalary(12500.00);
        double actual = employee.getSalary();
        assertEquals(12500.00,actual);
    }

    @Test
    void confirmEmployeePaid() {
        employee.setPaid(true);

        boolean confirmedPayment = employee.isPaid();
        assertTrue(confirmedPayment);
    }
    @Test
    void confirmEmployeeNotPaid() {
        employee.setPaid(false);
        boolean confirmedNotPaid = employee.isPaid();
        assertFalse(confirmedNotPaid);
    }

    @Test
    void settingConfirmedPaidEmployee() {
        employee.setPaid(true);
        boolean paid = employee.isPaid();
        assertTrue(paid);
    }
    @Test
    void settingUnConfirmedPaidEmployee() {
        employee.setPaid(false);
        boolean paid = employee.isPaid();
        assertFalse(paid);
    }

    @Test
    void testingEmployeeToStringFormat() {

        var action = employee.toString();
        assertEquals("Employee [id=1, salary=11500.0]", action);
    }
}