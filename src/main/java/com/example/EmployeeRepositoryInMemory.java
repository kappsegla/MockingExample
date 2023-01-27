package com.example;
import java.util.List;


public class EmployeeRepositoryInMemory implements EmployeeRepository {

    List<Employee> employees;

    public EmployeeRepositoryInMemory(List<Employee> employees) {
        this.employees = employees;
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


}
