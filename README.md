# Hibernate Envers Example 📚

This repository contains an example implementation of a RabbitMQ producer using Java Spring Boot.

## Depedencies

You can find the dependencies in the `pom.xml` file.

Here is Envers dependency:

```xml

<dependency>
  <groupId>org.springframework.data</groupId>
  <artifactId>spring-data-envers</artifactId>
</dependency>
```

## Some Technologies

| Language                                                                                                                                          | Description               |
|---------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------|
| <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" width="80" />                     | main language             |
| <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot" width="80" /> | framework used            |
| <img src="https://img.shields.io/badge/Hibernate-c4b889?style=for-the-badge&logo=hibernate&logoColor=white" alt="RabbitMQ" width="80" />          | message broker            |
| <img src="https://img.shields.io/badge/Lombok-FF2D20?style=for-the-badge&logo=lombok&logoColor=white" alt="Lombok" width="80" />                  | reducing boilerplate code |

## Running the Application

To run the application, use the following command:

```bash
mvn spring-boot:run
```

You can use Docker to run postgres database:

```bash
docker compose up
```
