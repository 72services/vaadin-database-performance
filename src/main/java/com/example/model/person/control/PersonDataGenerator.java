package com.example.model.person.control;

import com.example.model.person.entity.Person;

import java.time.LocalDateTime;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class PersonDataGenerator implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDataGenerator.class);

    private final PersonRepository personRepository;

    public PersonDataGenerator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) {
        if (personRepository.count() != 0L) {
            LOGGER.info("Using existing database");
            return;
        }
        LOGGER.info("Generating Person Data");

        LOGGER.info("... Generating 100 Sample Person Entities...");

        ExampleDataGenerator<Person> personDataGenerator = new ExampleDataGenerator<>(Person.class, LocalDateTime.now());

        personDataGenerator.setData(Person::setId, DataType.ID);
        personDataGenerator.setData(Person::setFirstName, DataType.FIRST_NAME);
        personDataGenerator.setData(Person::setLastName, DataType.LAST_NAME);
        personDataGenerator.setData(Person::setEmail, DataType.EMAIL);
        personDataGenerator.setData(Person::setPhone, DataType.PHONE_NUMBER);
        personDataGenerator.setData(Person::setDateOfBirth, DataType.DATE_OF_BIRTH);
        personDataGenerator.setData(Person::setOccupation, DataType.OCCUPATION);
        personDataGenerator.setData(Person::setImportant, DataType.BOOLEAN_10_90);

        personRepository.saveAll(personDataGenerator.create(100, 123));

        LOGGER.info("Generated Person Data");
    }
}
