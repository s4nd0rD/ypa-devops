# YPA DevOps training

The project is used for a DevOps training for the Young Professionals Academy of Incentro.

## Prerequisites

1. [Java SDK](https://java.com/en/download/)
2. [Maven](https://maven.apache.org/download.cgi) 
3. [Docker _(optional)_](https://www.docker.com/products/docker-desktop)

## Running the application

### Spring boot
If you want to run the application with Spring Boot:
```
mvn clean install
mvn spring-boot:run
```
After executing above commands, the application should be running on localhost:8080.

### Docker
Creating a Docker image requires to build the application first using `mvn clean install`.

Build a docker image with the following command:
```
docker build -t [IMAGE_NAME] .
```
Run the image with:
```
docker run -tid --rm -p 8080:8080 [IMAGE_NAME]
```

## Running the tests

Run `mvn test`

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE) file for details


