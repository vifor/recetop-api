# Recetop API

A Spring Boot REST API for managing recipes, built with Java 17, Spring Data JPA, and H2 in-memory database. The API uses DTOs for data transfer and supports OpenAPI documentation via springdoc.

## Features

- CRUD operations for recipes
- DTO mapping for API responses
- In-memory H2 database for development/testing
- OpenAPI (Swagger UI) documentation

## Requirements

The requirements for running this project depend on the method you choose.

* **To run locally using Maven:**
    * Java 17+
    * Maven
* **To run using Docker:**
    * Docker Desktop installed and running
## Getting Started

You can run this application in two ways: directly using Maven, or by running it as a Docker container (recommended).

### Option 1: Running with Maven

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/vifor/recetop-api.git](https://github.com/vifor/recetop-api.git)
    cd recetop-api
    ```

2.  **Build and run the application:**
    ```sh
    mvn spring-boot:run
    ```

### Option 2: Running with Docker (Recommended)

This is the recommended way to run the application as it is packaged in a self-contained environment.

**Prerequisites:**
* Docker Desktop must be installed and running on your machine.

**Steps:**

1.  **Build the Docker Image:**
    From the root directory of the project, run the following command to build the image:
    ```sh
    docker build -t vifor/recetop-api .
    ```

2.  **Run the Docker Container:**
    Once the image is built, run the following command to start the application inside a container:
    ```sh
    docker run -p 8080:8080 vifor/recetop-api
    ```

---
### Access the API

Once the application is running (either via Maven or Docker), you can access it at the following locations:

* **API root:** `http://localhost:8080/recipes`
* **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
* **H2 Console:** `http://localhost:8080/h2-console`
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
| GET         | `/recipes`              | Get all recipes (with pagination)  | See Pagination    | `Page<RecipeDto>`|
| POST   🔒    | `/recipes`        | Create a new recipe | `RecipeDto`         | `RecipeDto`      |
| PATCH  🔒       | `/recipes/{id}`         |Partially update a recipe. Only include the fields you want to change. | `Partial<RecipeDto>`       | `RecipeDto`      |
| DELETE 🔒      | `/recipes/{id}`         | Delete a recipe by ID              | -                 | -                |

> **Note:** Endpoints may require the correct JSON structure for `RecipeDto`.

### Pagination, Sorting, and Filtering

The endpoint that returns a list of resources, such as `GET /recipes`, supports pagination and sorting through URL query parameters.

**Available Parameters:**

* `page`: The page number you want to retrieve (0-indexed, default is `0`).
* `size`: The number of items to include per page (default is `20`).
* `sort`: A comma-separated list of properties to sort by. You can specify the direction by appending `,asc` (ascending, default) or `,desc` (descending).

**Example Usage:**

* **Get the first page with 5 recipes:**
    `GET /recipes?page=0&size=5`
* **Get the second page with 10 recipes:**
    `GET /recipes?page=1&size=10`
* **Get recipes sorted by name in descending order:**
    `GET /recipes?sort=name,desc`

**Response Structure:**

The response for a paginated endpoint is a `Page` object, which has the following structure:

```json
{
    "content": [ ... ], // The array of recipes for the current page
    "pageable": { ... },
    "totalPages": 10,
    "totalElements": 51,
    "last": false,
    "size": 5,
    "number": 0, // The current page number
    "sort": { ... },
    "numberOfElements": 5,
    "first": true,
    "empty": false
}

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
### Caching Strategy

This project leverages Spring Boot's caching abstraction (`spring-boot-starter-cache`) and the JCache (JSR-107) standard API.

**Provider:**
* The chosen JCache implementation is **Ehcache 3**, a robust and widely used in-memory caching library.

**Configuration:**
* The cache is configured in the `src/main/resources/ehcache.xml` file.
* To align with the rate limiter's 1-minute refill window, the cache that stores the rate-limiting buckets has an entry expiry of **1 minute**. This ensures that stale rate-limiting data is promptly evicted.

**Current Usage:**
* **Rate Limiting:** The primary consumer of the cache is the **Bucket4j** feature. It uses the configured `jcache` to store the token buckets for each user, keeping track of their request counts over time to enforce the defined limits.

## License

This project is licensed under the MIT License.