FROM maven:3.8.1-openjdk-17 AS build
RUN mkdir -p /app
COPY .. /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /app/target/busbooking-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]