# Build stage (usa imagen con Maven y JDK)
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage (JDK y app empaquetada)
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/quarkus-app /app
EXPOSE 8080
CMD ["java", "-jar", "quarkus-run.jar"]
