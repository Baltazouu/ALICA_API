# Use official OpenJDK 17 image as base
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container at the working directory
#COPY target/ALICA_API-0.0.1-SNAPSHOT.jar /app/ALICA_API.jar


# Copier les fichiers de configuration dans le conteneur
COPY src/main/resources/application-prod.properties /app/application-prod.properties

# Exposer le port sur lequel votre application Spring Boot s'exécutera
EXPOSE 80,433

# Commande pour exécuter l'application Spring Boot
CMD ["java", "-jar", "ALICA_API-0.0.1-SNAPSHOT.jar", "--spring.config.location=file:/app/application-prod.properties"]