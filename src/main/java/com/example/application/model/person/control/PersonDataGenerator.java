package com.example.application.model.person.control;

import com.example.application.model.person.entity.Person;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@Service
public class PersonDataGenerator implements CommandLineRunner {

    private final PersonRepository personRepository;

    public PersonDataGenerator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) {
        Logger logger = LoggerFactory.getLogger(getClass());
        if (personRepository.count() != 0L) {
            logger.info("Using existing database");
            return;
        }
        logger.info("Generating demo data");

        logger.info("... generating 100 Sample Person entities...");
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

        logger.info("Generated demo data");
    }
}
