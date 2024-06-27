# URL Shortener
A Spring Boot application that provides a RESTful API for creating and accessing shortened URLs.

## Features
- Create a short URL from a long URL.
- Redirect to the original URL using the short URL code.

## Tech Stack
- Java 22
- Spring Boot
- Hibernate
- Lombok
- H2 Database (for demonstration purposes)
- Gradle

## Getting Started

### Building and Running the Application
1. Build the application using Gradle: 
    ```sh
    ./gradlew clean build
    ```

2. Run the application: 
    ```sh
    ./gradlew bootRun
    ```

The application will start running on http://localhost:8080.

### Running Unit Tests
To run the unit tests, use the following command:
```sh
./gradlew test
```
This will run all the unit tests in the project and display the test results.

## Usage

### Create a Short URL
To create a new short URL, send a `POST` request to the `/api/v1/short-urls` endpoint with the original URL in the request body:
```sh
curl -X POST \
  http://localhost:8080/api/v1/short-urls \
  -H 'Content-Type: application/json' \
  -d '{"originalUrl": "https://www.example.com/very/long/url"}'
```

The response will contain the newly created ShortUrl object:
```json
{
  "id": 1,
  "shortCode": "eQhIja",
  "originalUrl": "https://www.example.com/very/long/url"
}
```

### Redirect to the Original URL
To redirect to the original URL, send a `GET` request to the `/api/v1/short-urls/{shortCode}` endpoint, where `{shortCode}` is the short code of the URL you want to access.
```sh
curl -i http://localhost:8080/api/v1/short-urls/eQhIja
```

If the short URL is found, the server will respond with a 302 Found status and the Location header will contain the original URL:
```http
HTTP/1.1 302 Found
Location: https://www.example.com/very/long/url
```
If the short URL is not found, the server will respond with a 404 Not Found status and an error message:
```http
HTTP/1.1 404 Not Found
Content-Length: 57
Content-Type: text/plain;charset=UTF-8

Short URL with code 'abc123' not found.
```

## License
This project is licensed under the [MIT](https://choosealicense.com/licenses/mit/) License.