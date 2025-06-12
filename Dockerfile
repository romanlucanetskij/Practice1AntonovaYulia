# Build stage
FROM amazoncorretto:17-debian-bookworm AS build

# Встанови Maven
RUN apt-get update && apt-get install -y curl gnupg \
    && curl -sL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y maven \
    && apt-get clean

# Встанови робочу директорію
WORKDIR /app

# Скопіюй весь проєкт і збери його
COPY . .
RUN ./mvnw clean package -DskipTests

# Final stage
FROM amazoncorretto:17-debian-bookworm
WORKDIR /app
COPY --from=build /app/target/Practice1AntonovaYulia-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "Practice1AntonovaYulia-0.0.1-SNAPSHOT.jar"]