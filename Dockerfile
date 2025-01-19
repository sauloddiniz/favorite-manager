FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/favorite-manager-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xms512m", "-Xmx1g", "-jar", "app.jar", "--spring.profiles.active=local"]

