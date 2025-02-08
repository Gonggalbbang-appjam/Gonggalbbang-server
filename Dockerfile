FROM openjdk:17

COPY ./build/libs/gonggalbbang-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]