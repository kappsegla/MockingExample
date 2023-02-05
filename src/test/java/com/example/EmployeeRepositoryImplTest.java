package com.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeRepositoryImplTest {

    EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl(List.of
            (new Employee("1", 12550.50), new Employee("2", 13550.50), new Employee("3", 14550.50)));

    @Test
    void findAllEmployeeOnTheList() {
        var result = employeeRepository.findAll().size();
        assertEquals(3, result );
    }
    @Test
    void findEmployeeOnTheListBySalary() {
        var result = employeeRepository.employees.get(0).getSalary();
        assertEquals(12550.50,result);
    }
    @Test
    void findEmployeeOnTheListById() {
        var result = employeeRepository.employees.get(0).getId();
        assertEquals("1",result);
    }


    @Test
    void savedNewEmployeeToTheList() {
        Employee employee = new Employee("3", 15550.50);
        employeeRepository.save(employee);
        var result = employeeRepository.findAll().size();

        assertEquals(3, result );
    }

    @Test
    void clearDatabase() {
        employeeRepository.clearDatabase();
        var result = employeeRepository.findAll().size();

        assertEquals(0 , result);

    }
}