# Build stage
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/Community-0.0.1-SNAPSHOT.jar Community.jar
COPY --from=build /src/main/resources/static /static
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Community.jar"]
