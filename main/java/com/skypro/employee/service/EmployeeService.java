package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;

@Service
public class EmployeeService {

    private final Map<Integer, Employee> employees = new HashMap<>();
    private final Map<Integer, Employee> employeesHighSalary = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public double getSalarySum() {
        return employees.values().stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public Employee getSalaryMin() {
        double min = Double.MAX_VALUE;
        int key = 0;
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            if (entry.getValue().getSalary() < min) {
                min = entry.getValue().getSalary();
                key = entry.getKey();
            }
        }
        return this.employees.get(key);
    }

    public Employee getSalaryMax() {
        double max = Double.MIN_VALUE;
        int key = 0;
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            if (entry.getValue().getSalary() > max) {
                max = entry.getValue().getSalary();
                key = entry.getKey();
            }
        }
        return this.employees.get(key);
    }

    public Collection<Employee> getAllEmployeesHighSalary() {
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            if (entry.getValue().getSalary() > getAverageSum()) {
                employeesHighSalary.put(entry.getKey(), entry.getValue());
            }
        }
        return this.employeesHighSalary.values();
    }

    public double getAverageSum () {
        return getSalarySum() / employees.size();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Employee name should be set");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());

        this.employees.put(employee.getId(), employee);
        return employee;
    }
}
