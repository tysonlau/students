FROM maven:3.8.5-openjdk-17 AS build
copy . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/students-0.0.1-SNAPSHOT.jar students.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","students.jar"]