FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . /app
RUN chmod +x mvnw
