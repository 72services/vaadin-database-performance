# Vaadin Database Performance

This is an example project for my talk **High-performance data access with Vaadin** at the [Vaadin Dev Day - Spring 2021](High-performance data access with Vaadin)

The project is a standard Maven project, so you can import it to your IDE of choice. [Read more how to set up a development environment](https://vaadin.com/docs/v14/flow/installing/installing-overview.html) for Vaadin projects (Windows, Linux, macOS). 

## Prerequisite

### Database

The example application uses [PostgreSQL database](https://www.postgresql.org/). You need to install it locally or run it with [Docker](https://www.docker.com/get-started).

Then create the database:

    create database vaadin;
    create user vaadin with encrypted password 'vaadin';
    grant all privileges on database vaadin to vaadin;

### Java

Java 16 is used. You can download it for example from [AdoptOpenJDK](https://adoptopenjdk.net/).

## Running and debugging the application

The first start will be a bit slower because testdata is inserted when starting the first time.

### Running the application from the command line.
To run from the command line, use `mvn spring-boot:run` and open http://localhost:8080 in your browser.

### Running and debugging the application in Intellij IDEA
- Make sure to run mvn compile once to generate the classes uses by [jOOQ](https://jooq.org)
- Locate the Application.java class in the Project view. It is in the src folder, under the main package's root.
- Right-click on the Application class
- Select "Debug 'Application.main()'" from the list

After the application has started, you can view it at http://localhost:8080/ in your browser. 
You can now also attach breakpoints in code for debugging purposes, by clicking next to a line number in any source file.

### Running and debugging the application in Eclipse
- Make sure to run mvn compile once to generate the classes uses by [jOOQ](https://jooq.org)
- Locate the Application.java class in the Package Explorer. It is in `src/main/java`, under the main package.
- Right-click on the file and select `Debug As` --> `Java Application`.

Do not worry if the debugger breaks at a `SilentExitException`. This is a Spring Boot feature and happens on every startup.

After the application has started, you can view it at http://localhost:8080/ in your browser.
