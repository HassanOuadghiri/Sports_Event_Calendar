# 🏟️ Sports Event Calendar

A Spring Boot web application for managing and viewing sports events, matches, teams, stages, and results. Built with Thymeleaf server-side rendering, Spring Data JPA, and an H2 in-memory database.

---

## Tech Stack

| Technology | Purpose |
|---|---|
| **Java 21** | Core programming language |
| **Spring Boot 4.0.4** | Application framework and auto-configuration |
| **Spring Data JPA** | Data access / persistence layer |
| **H2 Database** | In-memory database for development and testing |
| **Thymeleaf** | Server-side template engine for HTML views |
| **Jakarta Validation** | Input validation via bean annotations |
| **Lombok** | Reduces boilerplate (constructors, getters/setters) |
| **Spring Boot Test** | Testing framework (JUnit 5, Mockito, MockMvc) |

---

## Features

- Browse and filter matches by **date** and **sport type**
- View match scores, winners, stadiums, and tournament stages
- **Create new events** directly from the web interface
- RESTful JSON API for all entities
- Responsive dark-theme UI with mobile card layout
- Preloaded sample data on startup via `DataInitializer`
- Global error handling with structured error responses

---

## Prerequisites

- **Java 21** (JDK)
- **Maven** (or use the included `mvnw` / `mvnw.cmd` wrapper)

## Getting Started

```bash
# Clone the repository
git clone https://github.com/HassanOuadghiri/Sports_Event_Calendar.git
cd Sports_Event_Calendar/events

# Run the application (using Maven wrapper)
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows

# Or with Maven installed globally
mvn spring-boot:run
```

Open in your browser: **http://localhost:8082**

### Running Tests

```bash
./mvnw test        # Linux / macOS
mvnw.cmd test      # Windows
```

---

## Configuration

The application is configured via `src/main/resources/application.properties`:

| Property | Value | Description |
|---|---|---|
| `server.port` | `8082` | HTTP port |
| `spring.datasource.url` | `jdbc:h2:mem:testdb` | H2 in-memory DB URL |
| `spring.jpa.hibernate.ddl-auto` | `create` | Auto-create schema on startup |
| `spring.h2.console.enabled` | `true` | Enable H2 web console |
| `spring.h2.console.path` | `/h2-console` | H2 console URL path |

> **Note:** Data is stored in-memory and resets when the application stops.

---

## Database Schema

The database consists of 5 entities with the following relationships:

```
┌──────────┐     ┌───────────┐     ┌──────────┐
│  Team    │────▶│  Match    │◀────│  Team    │
│ (home)   │     │           │     │ (away)   │
└──────────┘     └─────┬─────┘     └──────────┘
                       │
              ┌────────┼────────┐
              ▼        ▼        ▼
         ┌────────┐ ┌───────┐ ┌──────────┐
         │ Result │ │ Stage │ │SportType │
         │  (1:1) │ │ (N:1) │ │  (N:1)   │
         └────────┘ └───────┘ └──────────┘
```

### Table: `matches`

| Column | Type | Constraints | Description |
|---|---|---|---|
| `id` | INTEGER | PK, auto-generated | Match ID |
| `status` | VARCHAR | NOT NULL | SCHEDULED, FINISHED, CANCELLED |
| `date_venue` | TIMESTAMP | NOT NULL | Match date and time |
| `match_group` | VARCHAR | nullable | Tournament group (e.g., "A") |
| `origin_competition_id` | VARCHAR | nullable | Competition identifier |
| `origin_competition_name` | VARCHAR | nullable | Competition name |
| `season` | INTEGER | NOT NULL | Season year |
| `home_team_id` | INTEGER | FK → team.id, NOT NULL | Home team |
| `away_team_id` | INTEGER | FK → team.id, NOT NULL | Away team |
| `result_id` | INTEGER | FK → result.id, UNIQUE | Match result |
| `stage_id` | VARCHAR | FK → stage.id | Tournament stage |
| `stadium` | VARCHAR | nullable | Venue name |
| `sport_type_id` | INTEGER | FK → sportType.id | Sport type |

### Table: `team`

| Column | Type | Constraints | Description |
|---|---|---|---|
| `id` | INTEGER | PK, auto-generated | Team ID |
| `name` | VARCHAR | NOT NULL | Display name |
| `official_name` | VARCHAR | nullable | Full official name |
| `slug` | VARCHAR | nullable | URL-friendly identifier |
| `abbreviation` | VARCHAR(5) | nullable | Short code (e.g., "POR") |
| `team_country_code` | VARCHAR(3) | nullable | ISO country code |
| `stage_position` | INTEGER | nullable | Position within group |

### Table: `result`

| Column | Type | Constraints | Description |
|---|---|---|---|
| `id` | INTEGER | PK, auto-generated | Result ID |
| `home_goals` | INTEGER | NOT NULL, ≥ 0 | Home team goals |
| `away_goals` | INTEGER | NOT NULL, ≥ 0 | Away team goals |
| `winner` | VARCHAR | nullable | HOME, AWAY, or DRAW |
| `message` | VARCHAR | nullable | Match summary text |
| `goals` | INTEGER | ≥ 0 | Total goals |
| `yellow_cards` | INTEGER | ≥ 0 | Yellow card count |
| `second_yellow_cards` | INTEGER | ≥ 0 | Second yellow count |
| `direct_red_cards` | INTEGER | ≥ 0 | Direct red card count |

### Table: `stage`

| Column | Type | Constraints | Description |
|---|---|---|---|
| `id` | VARCHAR | PK (manual) | Stage code (e.g., "GROUP") |
| `name` | VARCHAR | NOT NULL | Display name |
| `ordering` | INTEGER | NOT NULL, ≥ 1 | Sort order |

### Table: `sportType`

| Column | Type | Constraints | Description |
|---|---|---|---|
| `id` | INTEGER | PK, auto-generated | Sport type ID |
| `name` | VARCHAR | NOT NULL | Sport name |

---

## API Endpoints

### Thymeleaf Views (HTML)

| Method | Path | Description |
|---|---|---|
| `GET` | `/` | Landing page |
| `GET` | `/matches` | Match listing with filters |
| `GET` | `/matches?date=YYYY-MM-DD` | Filter matches by date |
| `GET` | `/matches?sportTypeId=N` | Filter matches by sport type |
| `POST` | `/matches` | Create a new match event (form submission) |

### REST API (JSON)

| Method | Path | Description | Sample Response |
|---|---|---|---|
| `GET` | `/api/v1/matches` | All matches | `[{"id":1, "status":"FINISHED", ...}]` |
| `GET` | `/api/v1/teams` | All teams | `[{"id":1, "name":"Portugal", ...}]` |
| `GET` | `/api/v1/result` | All results | `[{"id":1, "homeGoals":2, ...}]` |
| `GET` | `/api/v1/stages` | All stages | `[{"id":"GROUP", "name":"Group Stage", ...}]` |
| `GET` | `/api/v1/sport-types` | All sport types | `[{"id":1, "name":"Football"}]` |

### H2 Console

Available at `/h2-console` when the application is running. Use:
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** *(empty)*

---

## Project Structure

```
events/
├── src/main/java/com/sports/events/
│   ├── EventsApplication.java          # Spring Boot entry point
│   ├── DataInitializer.java            # Sample data seeding
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java # Centralized error handling
│   │   └── ErrorResponse.java         # Error response DTO
│   ├── match/
│   │   ├── Match.java                 # Entity
│   │   ├── MatchController.java       # Thymeleaf controller
│   │   ├── MatchRestController.java   # REST API controller
│   │   ├── MatchRepository.java       # JPA repository
│   │   └── MatchService.java         # Business logic
│   ├── result/
│   │   ├── Result.java, ResultController.java
│   │   ├── ResultRepository.java, ResultService.java
│   ├── sport/
│   │   ├── SportType.java, SportTypeController.java
│   │   ├── SportRepository.java, SportService.java
│   ├── stage/
│   │   ├── Stage.java, StageController.java
│   │   ├── StageRepository.java, StageService.java
│   └── team/
│       ├── Team.java, TeamController.java
│       ├── TeamRepository.java, TeamService.java
├── src/main/resources/
│   ├── application.properties
│   ├── templates/
│   │   ├── index.html                 # Landing page
│   │   ├── matches.html               # Match listing + create form
│   │   └── error.html                 # Custom error page
│   └── Documentation/                 # ERD diagram
└── src/test/java/com/sports/events/   # Unit & integration tests
```

---

## Data Seeding

The `DataInitializer` class implements `CommandLineRunner` and automatically seeds the database on startup with:

- **Sport Types:** Football, Basketball
- **Stages:** Group Stage, Round of 16, Quarter Final
- **Teams:** Portugal, Spain, France, Germany
- **Results:** 3 sample match outcomes
- **Matches:** 3 sample matches for "International Cup 2026"

---

## AI Disclosure

AI tools were used to assist with:
- Database schema generation from the ERD
- `DataInitializer` data seeding setup
- UI/template enhancement and responsive design
- Test scaffolding and code quality improvements

See `AI_Reflection.txt` for a detailed reflection on design decisions and challenges.

---

## License

This project is for educational and demonstration purposes.