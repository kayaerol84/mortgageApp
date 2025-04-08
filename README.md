# Mortgage Application Service

This is a Java-based backend application providing functionality for calculating mortgage feasibility and monthly costs. It uses Spring Boot, Spring Security, and other related technologies.

## Table of Contents

* [Prerequisites](#prerequisites)
* [Getting Started](#getting-started)
    * [Building the Application](#building-the-application)
    * [Running the Application](#running-the-application)
* [API Endpoints](#api-endpoints)
    * [GET /api/interest-rates](#get-apiinterest-rates)
    * [POST /api/mortgage-check](#post-apimortgage-check)
* [Authentication](#authentication)
* [Running Tests](#running-tests)

## Prerequisites

* **Java Development Kit (JDK):** Version 17 or higher is recommended.
* **Gradle:** You'll need Gradle to build the application.
* **Git:** To clone the repository (optional, if you download the ZIP).

## Getting Started

### Building the Application

1.  **Clone the repository (optional):**

    ```bash
    git clone <repository_url>
    cd mortgageApp
    ```

2.  **Build with Gradle:**

    ```bash
    ./gradlew clean build
    ```

    or if you have Gradle installed globally:

    ```bash
    gradle clean build
    ```

    This command will download dependencies, compile the code, and package the application into an executable JAR file in the `build/libs/` directory.

### Running the Application

1.  **Run the app via the Gradle command**
    ```bash
    ./gradlew bootRun
    ```

OR

2.  **Run the JAR file:**

    ```bash
    java -jar build/libs/mortgageApp-0.0.1-SNAPSHOT.jar
    ```

    (The exact filename may vary slightly depending on your project configuration.)


2.  The application will start, and you should see log messages indicating that it's running (on port 8080).

## API Endpoints

The application exposes the following REST API endpoints:

### GET /api/interest-rates

* **Description:** Retrieves a list of current mortgage interest rates.
* **Method:** GET
* **Request:** None
* **Response:** A JSON array of `MortgageRate` objects. Each object contains:
    * `maturityPeriod` (integer): The maturity period of the mortgage in years.
    * `interestRate` (number): The annual interest rate (e.g., 0.045 for 4.5%).
    * `lastUpdate` (instant): The last update timestamp of the interest rate.
* **Example (using curl):**

    ```bash
    curl http://localhost:8080/api/interest-rates
    ```

* **Example Response:**

    ```json
    [
        {
            "maturityPeriod": 10,
            "interestRate": 0.035,
            "lastUpdate": "2024-03-10T10:00:00.000+00:00"
        },
        {
            "maturityPeriod": 20,
            "interestRate": 0.040,
            "lastUpdate": "2024-03-10T10:00:00.000+00:00"
        },
        {
            "maturityPeriod": 30,
            "interestRate": 0.045,
            "lastUpdate": "2024-03-10T10:00:00.000+00:00"
        }
    ]
    ```

### POST /api/mortgage-check

* **Description:** Performs a mortgage feasibility check and calculates the estimated monthly costs.
* **Method:** POST
* **Request:** A JSON object with the following fields:
    * `income` (number): The applicant's annual income (e.g., 100000).
    * `maturityPeriod` (integer): The desired mortgage maturity period in years.
    * `loanValue` (number): The total amount of the loan requested.
    * `homeValue` (number): The appraised value of the property.
    * `currency` (string): The currency of the amounts.
* **Response:** A JSON object with the following fields:
    * `feasible` (boolean): Indicates whether the mortgage is considered feasible based on the business rules.
    * `monthlyCosts` (number): The estimated monthly mortgage payment amount.
    * `currency` (string): The currency of the monthly costs.
* **Example (using curl):**

With currency: 

```bash
    curl -H "Content-Type: application/json" -X POST -d '{
        "income": 100000,
        "maturityPeriod": 20,
        "loanValue": 300000,
        "homeValue": 400000,
        "currency": "USD"
    }' http://localhost:8080/api/mortgage-check
```

With default currency:

```bash
    curl -H "Content-Type: application/json" -X POST -d '{
        "income": 100000,
        "maturityPeriod": 20,
        "loanValue": 300000,
        "homeValue": 400000
    }' http://localhost:8080/api/mortgage-check
```

* **Example Request Body:**

    ```json
    {
        "income": 100000,
        "maturityPeriod": 20,
        "loanValue": 300000,
        "homeValue": 400000
    }
    ```

* **Example Response:**

    ```json
    {
        "feasible": true,
        "monthlyCosts": 1815.93,
        "currency":"EUR"
    }
    ```

## Authentication

Neglected

## Running Tests

The application includes unit tests to ensure code quality and correctness.

1.  **Run tests with Gradle:**

    ```bash
    ./gradlew test
    ```

    or

    ```bash
    gradle test
    ```

    These commands will execute all the unit tests in the project. The test results will be displayed in the console.
