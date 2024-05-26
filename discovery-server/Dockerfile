# Sử dụng hình ảnh cơ bản của Maven để build ứng dụng
FROM jelastic/maven:3.9.5-openjdk-22.ea-b19 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Sử dụng hình ảnh cơ bản của JDK 22 để chạy ứng dụng
FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /app/target/*.jar /app/discovery-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app/discovery-server.jar"]