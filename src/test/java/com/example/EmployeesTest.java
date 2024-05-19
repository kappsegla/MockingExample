package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeesTest {


    @Mock
    EmployeeRepository employeeRepository;
    @Spy
    BankService bankService;




    @BeforeEach
    void init(){

        Employee employee1 = new Employee("10", 20000);
        Employee employee2 = new Employee("11", 20000);
        Employee employee3 = new Employee("12", 20000);
        List<Employee> employees = List.of(employee1,employee2,employee3);
        when (employeeRepository.findAll()).thenReturn(employees);
    }

    @Test
    @DisplayName("Test payEmployees returns 3 when paying 3 employees")
    void testPayEmployeesReturns3WhenPaying3Employees(){
        Employees employees = new Employees(employeeRepository, bankService);
        var result = employees.payEmployees();
        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("Test payEmployees returns 0 with null bankService")
    void givenNullInsteadOfBankservicePayEmployeesShouldReturnZero(){
        Employees employees = new Employees(employeeRepository,null);
        employeeRepository.findAll().forEach(e -> e.setPaid(true));
        var result = employees.payEmployees();
        assertThat(result).isZero();
    }
}



