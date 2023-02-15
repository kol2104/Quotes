FROM openjdk:8-jdk-alpine
WORKDIR /usr/src/users
COPY ./target/users-0.0.1-SNAPSHOT.jar /usr/src/users
EXPOSE 8081
CMD ["java", "-jar", "users-0.0.1-SNAPSHOT.jar"]