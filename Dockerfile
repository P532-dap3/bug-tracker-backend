FROM eclipse-temurin:17
WORKDIR /home
COPY ./target/bug_tracker-0.0.1-SNAPSHOT.jar bugtracker.jar
ENTRYPOINT ["java", "-jar", "bugtracker.jar"]