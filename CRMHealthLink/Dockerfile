FROM openjdk:17-alpine

WORKDIR app

COPY . .
RUN apk add maven

RUN mvn clean
RUN mvn install -DskipTests

CMD mvn spring-boot:run

EXPOSE 8080

CMD ["java", "-jar","target/CRMHealthLink-0.0.1-SNAPSHOT.jar"]