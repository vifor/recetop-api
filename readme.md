# Recetop API

A Spring Boot REST API for managing recipes, built with Java 17, Spring Data JPA, and H2 in-memory database. The API uses DTOs for data transfer and supports OpenAPI documentation via springdoc.

## Features

- CRUD operations for recipes
- DTO mapping for API responses
- In-memory H2 database for development/testing
- OpenAPI (Swagger UI) documentation

## Requirements

- Java 17+
- Maven

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/vifor/recetop-api.git
   cd recetop-api
   ```

2. **Build and run the application:**
   ```sh
   mvn spring-boot:run
   ```

3. **Access the API:**
   - API root: [http://localhost:8080/recipes](http://localhost:8080/recipes)
   - Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html#/)
   - H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

## Authentication

Protected endpoints on this API require a Bearer Token to be sent in the `Authorization` header.

**Header Format:**
`Authorization: Bearer <your_token>`

For development and testing purposes, the following hardcoded token can be used:
`my-super-secret-jwt-token-for-testing`

## API Endpoints

| HTTP Method | Endpoint                | Description                        | Request Body      | Response         |
|-------------|-------------------------|------------------------------------|-------------------|------------------|
| GET         | `/recipes/newcomer`     | Get the first (newcomer) recipe    | -                 | `RecipeDto`      |
| GET         | `/recipes/{id}`         | Get a recipe by ID                 | -                 | `RecipeDto`      |
| GET         | `/recipes`              | Get all recipes                    | -                 | `List<RecipeDto>`|
| POST   🔒    | `/recipes`        | Create a new recipe | `RecipeDto`         | `RecipeDto`      |
| PATCH  🔒       | `/recipes/{id}`         |Partially update a recipe. Only include the fields you want to change. | `Partial<RecipeDto>`       | `RecipeDto`      |
| DELETE 🔒      | `/recipes/{id}`         | Delete a recipe by ID              | -                 | -                |

> **Note:** Endpoints may require the correct JSON structure for `RecipeDto`.

## Testing with Postman

A Postman collection is included to make testing the API endpoints easy. You can import the collection to your Postman client to get started right away.

1.  Download the collection file located at `postman/recetop-api.postman_collection.json`.
2.  In Postman, click the "Import" button.
3.  Upload the downloaded file. A new collection named "recetop-api" will appear in your collections tab.

## Testing

This project uses the **Karate Framework** for API end-to-end testing. Karate was chosen for its ability to combine API test automation, mocks, and performance testing into a single, unified framework. Its human-readable Gherkin syntax makes tests easy to write and understand without requiring deep Java knowledge for test creation.

### How to Run the Tests

The tests can be executed in two primary ways:

**1. From Your IDE**

The simplest way to run the tests is directly from your IDE (like IntelliJ or VS Code):
* Make sure the main Spring Boot application is running.
* Navigate to the test runner file at `src/test/java/com/recetop/api/recipes/RecipesKarateTest.java`.
* Click the "run" icon next to the class name to execute all Karate tests.

**2. Using Maven**

You can also run the tests from the command line using the Maven wrapper included with the project.

```sh
# This command will execute all tests, including the Karate tests
mvn test
```
## Technologies Used

- Spring Boot
- Spring Data JPA
- H2 Database
- Springdoc OpenAPI

## Architectural Decisions

This section outlines some of the key design choices made in this project.

### Authentication Strategy: Stateless JWT vs. Stateful Sessions

A stateless authentication mechanism using JSON Web Tokens (JWT) was chosen over traditional stateful sessions for several key reasons:

* **Scalability:** Stateless tokens do not require the server to store session information. This makes it much easier to scale the application horizontally by adding more server instances without worrying about session replication or sticky sessions.
* **Decoupling:** The token is self-contained. This makes it ideal for modern, decoupled architectures where multiple clients (e.g., a web frontend, a mobile app) might consume the API.
* **Industry Standard:** JWT Bearer authentication is the de-facto standard for securing modern RESTful APIs.

### Key Implementation Components

The security is implemented using the following core Spring and project components:

* **`pom.xml`:** Includes the `spring-boot-starter-security` and `jjwt` dependencies to bring in the necessary libraries.
* **`SecurityConfig.java`:** The central configuration class where the Spring Security filter chain is defined. It disables CSRF, sets the session management policy to `STATELESS`, and defines which endpoints are protected.
* **`JwtAuthenticationFilter.java`:** A custom filter that runs on every request. It is responsible for inspecting the `Authorization` header, validating the JWT, and setting the user's authentication context within Spring Security.
* **`springdoc-openapi` Annotations:** The `@SecurityScheme` and `@SecurityRequirement` annotations are used in the main application class (`ApiApplication.java`) and controllers (`RecipeController.java`) to accurately document the security requirements in the Swagger UI.

## License

This project is licensed under the MIT License.