FROM openjdk:8-jdk-alpine
VOLUME /tmp
# TODO: Copy artifacts
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]