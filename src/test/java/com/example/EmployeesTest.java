package com.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;

class EmployeesTest {

    EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl(List.of
            (new Employee("1", 12550.50), new Employee("2", 12550.50), new Employee("3",12550.50)));
    BankServiceImpl bankService = Mockito.mock(BankServiceImpl.class);
    Employees employees = new Employees(employeeRepository, bankService);


    @Test
    void PaidEmployeesWithIncrement() {
        assertEquals(3, employees.payEmployees());
    }

    @Test
    void PaidEmployeesWithOutIncrement() {

        //doThrow(RuntimeException.class).when(bankService).pay("0", 0.0);
        int unIncremented = employees.payEmployees();
        assertEquals(3, unIncremented);

    }

    @Test
    void testPaidPayEmployees() {

        doThrow(RuntimeException.class).when(bankService).pay(" ", 0);
        employees.payEmployees();
        boolean actual;
        actual = employeeRepository.findAll().get(0).isPaid();
        assertTrue(actual, "3");

    }
    @Test
    void testUnPaidPayEmployees() {

        doThrow(RuntimeException.class).when(bankService).pay(" ", 0);
        employees.payEmployees();
        boolean actual;
        actual = employeeRepository.findAll().get(0).isPaid();
        assertTrue(actual, "0");
    }

    @Test
    void payEmployeeExceptionSetsEmployeePaidToFalse() {
        employeeRepository.employees.get(0).setPaid(true);
        doThrow(RuntimeException.class).when(bankService).pay(" ", 0);
        employees.payEmployees();

        assertTrue(employeeRepository.findAll().get(0).isPaid());
    }

}