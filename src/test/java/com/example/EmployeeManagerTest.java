package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EmployeeManagerTest {

    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @Mock
    private BankService bankServiceMock;

    @Test
    public void testPayEmployeesWithTwoEmployeesShouldReturnTwoPayments() {
        EmployeeManager employeeManager = new EmployeeManager(employeeRepositoryMock, bankServiceMock);

        Mockito.when(employeeRepositoryMock.findAll()).thenReturn(List.of(new Employee("test1", 12345), new Employee("test2", 67890)));

        int actualPayments = employeeManager.payEmployees();

        Mockito.verify(employeeRepositoryMock, Mockito.times(1)).findAll();

        Assertions.assertEquals(2, actualPayments, "Should return two payments");
    }

    @Test
    public void testPayEmployeesWithNoEmployeesShouldReturnZeroPayments() {
        EmployeeManager employeeManager = new EmployeeManager(new EmployeeRepositoryStub(), new BankServiceDummy());
        int actualPayments = employeeManager.payEmployees();

        Assertions.assertEquals(0, actualPayments, "Should return zero payments");

    }
}
