# Dockerfile to build the application on Render
FROM maven:3.8.5-openjdk-17
COPY /src /app/src
COPY pom.xml /app/
WORKDIR /app
# Disable tests on Render
RUN mvn clean package -DskipTests
EXPOSE 8080