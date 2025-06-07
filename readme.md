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

## API Endpoints

| HTTP Method | Endpoint                | Description                        | Request Body      | Response         |
|-------------|-------------------------|------------------------------------|-------------------|------------------|
| GET         | `/recipes/newcomer`     | Get the first (newcomer) recipe    | -                 | `RecipeDto`      |
| GET         | `/recipes/{id}`         | Get a recipe by ID                 | -                 | `RecipeDto`      |
| GET         | `/recipes`              | Get all recipes                    | -                 | `List<RecipeDto>`|
| POST        | `/recipes`              | Create a new recipe                | `RecipeDto`       | `RecipeDto`      |
| PUT         | `/recipes/{id}`         | Update a recipe by ID              | `RecipeDto`       | `RecipeDto`      |
| DELETE      | `/recipes/{id}`         | Delete a recipe by ID              | -                 | -                |

> **Note:** Endpoints may require the correct JSON structure for `RecipeDto`.

## Testing with Postman

A Postman collection is included to make testing the API endpoints easy. You can import the collection to your Postman client to get started right away.

1.  Download the collection file located at `postman/recetop-api.postman_collection.json`.
2.  In Postman, click the "Import" button.
3.  Upload the downloaded file. A new collection named "recetop-api" will appear in your collections tab.

## Technologies Used

- Spring Boot
- Spring Data JPA
- H2 Database
- Springdoc OpenAPI

## License

This project is licensed under the MIT License.