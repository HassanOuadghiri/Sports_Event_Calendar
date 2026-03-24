# Sports Events App

A Spring Boot web application for managing and viewing sports events, matches, teams, stages, and results. The application uses an in-memory H2 database and Thymeleaf for server-side rendered views.

## Tech Overview

This project is built with the following technologies:

- **Java** – core programming language
- **Spring Boot** – application framework and runtime configuration
- **Spring Data JPA** – data access and persistence layer
- **H2 Database** – in-memory database for local development and testing
- **Thymeleaf** – server-side template engine for rendering web pages
- **Lombok** – reduces boilerplate code such as constructors and dependency injection setup
- **AI-assisted development** – used to support database generation, data seeding setup, and UI/template enhancement

## Features

- Manage sports event data
- Store and retrieve matches, teams, stages, sport types, and results
- Render views with Thymeleaf
- Run with an embedded server using Spring Boot
- Preload sample data automatically on startup
- Enhanced Thymeleaf template design for a better user interface and presentation

## Application Port

The application runs on port:

`8082`

Open it in your browser at:

`http://localhost:8082`

## Database

The project uses an **H2 in-memory database**, which means the data is stored temporarily while the application is running.

This is useful for:

- local development
- testing
- demo purposes

When the application stops, the in-memory data is reset unless additional persistence is configured.

### Database Design

The database structure was created with the help of **AI** by providing the system with the **ERD** (Entity Relationship Diagram) of the project.

You can find the database ERD in:

`src/main/resources/documentation`

## Data Initialization

The project includes a startup data seeding class:

`com.sports.events.DataInitializer`

This class implements `CommandLineRunner`, so it runs automatically when the application starts.

Its purpose is to insert sample data into the database, including:

- sport types
- stages
- teams
- results
- matches

The `DataInitializer` class was also created with the help of **AI** to automate the insertion of sample data whenever the application starts.

The initializer adds example data such as:

- **Sport Types**: Football, Basketball
- **Stages**: Group Stage, Round of 16, Quarter Final
- **Teams**: Portugal, Spain, France, Germany
- **Results**: sample home win, draw, and away win outcomes
- **Matches**: sample finished matches for the **International Cup 2026**

This makes the application ready to use immediately after startup without requiring manual data entry.

## User Interface

The Thymeleaf templates were enhanced and improved for a better visual design and user experience.

This includes improvements to the overall layout, readability, and presentation of the application pages.

## Project Structure

A simplified overview of the domain includes:

- `match` – match entity and repository
- `result` – result entity and repository
- `sport` – sport type entity and repository
- `stage` – tournament stage entity and repository
- `team` – team entity and repository
- `DataInitializer` – inserts startup data into the database
- `resources/documentation` – contains the database ERD documentation

## Running the Project

### Prerequisites

Make sure you have installed:

- Java 17+ (or the version required by your project)
- Maven or Gradle, depending on your build setup

### Run the application

If you are using Maven:

```bash
mvn spring-boot:run
```
Then open:



http://localhost:8082