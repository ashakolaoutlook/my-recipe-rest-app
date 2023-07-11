# Recipe REST API

This is a standalone RESTful API application for managing recipes. It provides endpoints to create, update, delete, and retrieve recipe information. The API also supports filtering recipes based on various criteria such as cooking time, servings, ingredients, and more.

## Technologies Used

- Java
- Spring Boot
- Spring Web
- Spring Security
- OAuth2
- Swagger (OpenAPI)
- MySQL
- Maven

## Getting Started

To get started with the Recipe REST API, follow these steps:

1. Clone the repository or download the source code(https://github.com/ashakolaoutlook/my-recipe-rest-app.git).
2. Make sure you have Java and Maven (or your preferred build tool) installed on your machine.
3. Open the project in your preferred IDE.
4. Build the project to download the required dependencies.
5. Configure the database connection in the `application.yml` file.
6. Configure the OAuth2 settings for GitHub registration in the `application.yml` file.
7. Run the application.
8. Access the API using the provided endpoints.

## API Documentation

The API documentation is available using Swagger. Once the application is running, you can access the Swagger UI by navigating to `http://localhost:8080/swagger-ui/index.html` in your web browser. The Swagger UI provides detailed information about the available endpoints, request/response structures, and allows you to test the API directly.

## Endpoints

The following are the main endpoints provided by the Recipe REST API:

```java
POST /api/v1/recipe - Create a new recipe.
PUT /api/v1/recipe - Update an existing recipe.
DELETE /api/v1/recipe/{id} - Delete a recipe by ID.
GET /api/v1/recipe/{id} - Get a recipe by ID.
GET /api/v1/recipes - Get all recipes.
GET /api/v1/recipes/byCookingTimeAndServings - Get recipes by cooking time and servings.
GET /api/v1/recipes/byVegetarian - Get recipes with veg/non-veg filter.
GET /api/v1/recipes/byServings - Get recipes by servings.
GET /api/v1/recipes/byInstructions - Get recipes by instructions.
GET /api/v1/recipe/vegetarian - Check if a dish is vegetarian based on title.
GET /api/v1/recipes/byIngredients - Get recipes by ingredients.
GET /api/v1/recipes/byServingsAndIngredient - Get recipes by servings and ingredient.
GET /api/v1/recipes/byInstructionsAndNotIngredientName - Search recipes by instructions and exclude ingredient name.
GET /api/v1/status - Get application status.
```
Please refer to the Swagger UI documentation for more details on each endpoint, including request/response structures and available parameters.

## OAuth2 Security with GitHub Registration

This application uses OAuth2 security for authentication. Specifically, it supports GitHub registration as an OAuth2 client. The configuration for GitHub registration can be found in the `application.yml` file. Please ensure you have provided the necessary client registration details for GitHub OAuth2 in the `application.yml` file before running the application.

## Testing

The Recipe REST API includes both unit tests and integration tests to ensure the correctness of the application. You can run the tests using your preferred testing framework.

## Production Readiness

The Recipe REST API is designed with production readiness in mind. It follows best practices in software engineering, including modular and clean code architecture, proper exception handling, and secure authentication using OAuth2 with GitHub registration. It is recommended to further configure the application for deployment, including proper logging, monitoring, and scaling considerations.

## Data Persistence

The Recipe REST API uses MySQL as the database for persisting recipe data. Please ensure you have set up a MySQL database and configured the database connection details in the `application.yml` file before running the application.

## Contributing

Contributions to the Recipe REST API are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## Contact

For any questions or inquiries, please contact Asha Kolakaluri (mailto:ashakolakaluri@outlook.com).
