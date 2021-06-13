package com.example.model.employee.control;

import com.example.model.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    int countBySupervisorIsNull();

    int countBySupervisor(Employee supervisor);

    List<Employee> findAllBySupervisorIsNull();

    List<Employee> findAllBySupervisor(Employee employee);
}
