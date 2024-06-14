FROM maven:3.8.1-openjdk-16-slim

WORKDIR /app

COPY . /app

#COPY path/to/postgresql-driver.jar /app/postgresql-driver.jar

RUN mvn package -DskipTests

CMD ["java", "-jar", "target/bahruz-eldar-heybat-step-project-1.0-SNAPSHOT.jar"]
