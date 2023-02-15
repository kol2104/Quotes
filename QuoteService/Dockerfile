FROM openjdk:8-jdk-alpine
WORKDIR /usr/src/quotes
COPY ./target/quotes-0.0.1-SNAPSHOT.jar /usr/src/quotes
EXPOSE 8082
CMD ["java", "-jar", "quotes-0.0.1-SNAPSHOT.jar"]