FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace

# copy only what is needed for dependency resolution first for better caching
COPY pom.xml mvnw pom.xml
COPY .mvn .mvn
COPY src src

RUN mvn -B -DskipTests package -DskipITs

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/appointment-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
