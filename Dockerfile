# Dockerfile to build the application on Render
FROM maven:3.8.5-openjdk-17 AS maven
COPY /src /app/src
COPY pom.xml /app/
WORKDIR /app
# Disable tests on Render
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=maven /app/target/*.jar /app/techpoker.jar
EXPOSE 8080
CMD java -jar /app/techpoker.jar