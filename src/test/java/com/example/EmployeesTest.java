package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

class EmployeesTest {

    EmployeeRepositoryStub employeeRepositoryStub = new EmployeeRepositoryStub();
    BankService bankService = Mockito.mock(BankService.class);
    Employees employees = new Employees(employeeRepositoryStub, bankService);

    @BeforeEach
    void setUp() {
        employeeRepositoryStub.save(new Employee("001",40000));
        employeeRepositoryStub.save(new Employee("002",45000));
        employeeRepositoryStub.save(new Employee("003",50000));
    }

    @Test
    void correctNumberOfEmployeesShouldHaveBenPaid() {
        int payments = employees.payEmployees();
        assertEquals(3, payments);
    }

    @Test
    void payMethodShouldBeCalled() {
        employees.payEmployees();
        Mockito.verify(bankService, Mockito.times(employeeRepositoryStub.findAll().size()))
                .pay(anyString(), anyDouble());
    }

    @Test
    void paymentShouldNotGoThroughWhenExceptionIsThrown() {
        doThrow(new IllegalArgumentException()).when(bankService).pay(anyString(),anyDouble());
        int payments = employees.payEmployees();
        assertEquals(0, payments);
    }
}





