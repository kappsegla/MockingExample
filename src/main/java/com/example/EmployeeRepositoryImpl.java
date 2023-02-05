package com.example;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    List<Employee> employees = new ArrayList<>();

    public EmployeeRepositoryImpl(List<Employee> employeeList) {
        this.employees.addAll(employeeList);
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee save(Employee e) {
        employees.removeIf(employee -> employee.getId().equals(e.getId()));
        employees.add(e);
        return e;
    }

    public void clearDatabase(){
        employees.clear();
    }
}
