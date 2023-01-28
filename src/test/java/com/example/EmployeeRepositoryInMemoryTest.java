package com.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


class EmployeeRepositoryInMemoryTest {

    EmployeeRepositoryInMemory employeeRepositoryInMemory;
    List<Employee> employees;

    @BeforeEach
    void setUp() {
        employeeRepositoryInMemory = new EmployeeRepositoryInMemory(employees = new ArrayList<>());
        employeeRepositoryInMemory.save(new Employee("001", 40000));
        employees.add(new Employee("002", 45000));
        employees.add(new Employee("003", 50000));

    }

    @Test
    void findAllShouldHaveCorrectSize() {
        int size = employeeRepositoryInMemory.findAll().size();
        assertThat(size).isEqualTo(3);
    }

    @Test
    void saveShouldReplaceEmployeeIfEmployeeIdExists() {
        employeeRepositoryInMemory.save(new Employee("003", 60000));
        int size = employeeRepositoryInMemory.findAll().size();
        assertThat(size).isEqualTo(3);
    }

    @Test
    void saveShouldAddEmployeeIfEmployeeIdDontExists() {
        employeeRepositoryInMemory.save(new Employee("004", 50000));
        int size = employeeRepositoryInMemory.findAll().size();
        assertThat(size).isEqualTo(4);
    }

    @Test
    void employeeShouldBeReplacedByEmployeeWithSameIdButHigherSalary(){

        employeeRepositoryInMemory.save(new Employee("003",60000));
        var newSalary = Objects.requireNonNull(employeeRepositoryInMemory.findAll().stream()
                        .filter(e -> e.getId().equals("003"))
                        .findFirst()
                        .orElse(null))
                .getSalary();

        assertThat(newSalary).isEqualTo(60000);
    }
}