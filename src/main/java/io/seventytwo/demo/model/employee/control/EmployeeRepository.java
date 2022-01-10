package io.seventytwo.demo.model.employee.control;

import io.seventytwo.demo.model.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    int countBySupervisorIsNull();

    int countBySupervisor(Employee supervisor);

    List<Employee> findAllBySupervisorIsNull();

    List<Employee> findAllBySupervisor(Employee employee);
}
