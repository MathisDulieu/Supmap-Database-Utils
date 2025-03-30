FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY pom.xml .
COPY settings.xml /tmp/settings.xml

RUN chmod +x ./mvnw

COPY src/ src/

RUN ./mvnw clean install -DskipTests

CMD ["./mvnw", "deploy", "-DskipTests", "-s", "/tmp/settings.xml"]