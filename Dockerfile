## Utiliser une image de base avec Java
#FROM openjdk:17-jdk-slim
#
## Définir le répertoire de travail dans le conteneur
#WORKDIR /app
#
## Copier le fichier pom.xml dans le répertoire de travail
#COPY pom.xml .
#
## Copier tout le contenu du répertoire de construction (à l'exception de Dockerfile) dans le répertoire de travail
#COPY src ./src
#
## Exécuter Maven pour construire le projet
#RUN apt-get update && apt-get install -y maven && mvn package -DskipTests
#
## Commande à exécuter lorsque le conteneur démarre
#CMD ["java", "-jar", "target/ALICA_API-0.0.1-SNAPSHOT.jar"]


FROM openjdk:18
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]