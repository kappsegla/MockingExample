package com.example;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryStub implements EmployeeRepository{

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Employee save(Employee e) {
        return null;
    }
}
