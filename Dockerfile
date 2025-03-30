FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY pom.xml .
COPY settings.xml /tmp/settings.xml

RUN chmod +x ./mvnw

COPY src/ src/

ARG NEXUS_USERNAME
ARG NEXUS_PASSWORD

RUN ./mvnw clean install -DskipTests -Dnexus.username=${NEXUS_USERNAME} -Dnexus.password=${NEXUS_PASSWORD}

CMD ["./mvnw", "deploy", "-DskipTests", "-s", "/tmp/settings.xml"]