FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/quarkusJekins-app-1.0.0-SNAPSHOT-runner.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]
