package com.example;

import java.util.ArrayList;
import java.util.List;

class EmployeeRepositoryStub implements EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee save(Employee e) {
        employees.add(e);
        return e;
    }
}
