FROM maven:3.8.6-amazoncorretto-17 as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/rpgstats-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/rpgstats.jar
EXPOSE 8080
