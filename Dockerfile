# Stage 1: Build
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copier les fichiers de configuration Gradle
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle

# Télécharger les dépendances (cache layer)
RUN gradle dependencies --no-daemon

# Copier le code source
COPY src ./src

# Build du fat JAR
RUN gradle buildFatJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Créer un utilisateur non-root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copier le JAR depuis le stage de build
COPY --from=build /app/build/libs/*-all.jar app.jar

# Changer le propriétaire
RUN chown -R appuser:appgroup /app
USER appuser

# Port exposé (ajuster selon votre config)
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
