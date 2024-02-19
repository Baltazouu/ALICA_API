FROM maven as maven
LABEL MAINTAINER="baptiste.dudonne@etu.uca.fr"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package

# For Java 11,
FROM openjdk
ARG JAR_FILE=ALICA_API-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

# Copy the spring-boot-api-tutorial.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

EXPOSE 80
EXPOSE 433

ENTRYPOINT ["java","-jar","ALICA_API-0.0.1-SNAPSHOT.jar","--spring.config.location=file:/app/application-prod.properties"]
