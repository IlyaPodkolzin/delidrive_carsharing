FROM jelastic/maven:3.9.5-openjdk-21
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package
CMD ["java", "-jar", "target/docker_maven-0.0.1-SNAPSHOT.jar"]