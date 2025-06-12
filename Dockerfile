# Build stage
FROM maven:3.9-jdk-17 AS build

# Встанови робочу директорію
WORKDIR /app

# Скопіюй весь проєкт і збери його
COPY . .
RUN ./mvnw clean package -DskipTests

# Final stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Practice1AntonovaYulia-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "Practice1AntonovaYulia-0.0.1-SNAPSHOT.jar"]
