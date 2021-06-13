package com.example.model.employee.control;

import com.example.model.employee.entity.Employee;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import java.time.LocalDateTime;

@SpringComponent
public class EmployeeDataGenerator implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDataGenerator.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeDataGenerator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (employeeRepository.count() != 0L) {
            LOGGER.info("Using existing database");
            return;
        }
        LOGGER.info("Generating Employee Data");

        LOGGER.info("... Generating 100 Sample Employee Entities...");

        var dataGenerator = new ExampleDataGenerator<>(Employee.class, LocalDateTime.now());
        dataGenerator.setData(Employee::setId, DataType.ID);
        dataGenerator.setData(Employee::setFirstName, DataType.FIRST_NAME);
        dataGenerator.setData(Employee::setLastName, DataType.LAST_NAME);
        dataGenerator.setData(Employee::setEmail, DataType.EMAIL);
        dataGenerator.setData(Employee::setPhone, DataType.PHONE_NUMBER);
        dataGenerator.setData(Employee::setDateOfBirth, DataType.DATE_OF_BIRTH);
        dataGenerator.setData(Employee::setOccupation, DataType.OCCUPATION);
        dataGenerator.setData(Employee::setImportant, DataType.BOOLEAN_10_90);

        int seed = 123;

        var boss = dataGenerator.create(1, seed).get(0);
        employeeRepository.save(boss);

        seed = seed * 123;
        var managers = dataGenerator.create(2, seed);
        employeeRepository.saveAll(managers);

        for (Employee manager : managers) {
            manager.setSupervisor(boss);

            seed = seed * 123;
            var employees = dataGenerator.create(5, seed++);
            employees.forEach(employee -> employee.setSupervisor(manager));

            employeeRepository.saveAll(employees);
        }

        employeeRepository.saveAll(managers);

        LOGGER.info("Generated Employee Data");
    }
}
