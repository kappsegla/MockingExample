package com.example;

import java.util.ArrayList;
import java.util.List;


public class EmployeeRepositoryInMemory implements EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee save(Employee e) {
        employees.replaceAll(employee -> e.getId().equals(employee.getId()) ? e : employee);
    return e;

    }


}
