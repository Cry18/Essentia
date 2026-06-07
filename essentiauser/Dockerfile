# ── Stage 1 — Build ────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /workspace

# Step 1: install essentia-common into the container's local Maven repository.
# essentiauser does not directly import common DTOs, but it is installed here
# for consistency and to future-proof the build if common is extended later.
COPY essentia-common/pom.xml essentia-common/pom.xml
RUN cd essentia-common && mvn dependency:go-offline -B

COPY essentia-common/src essentia-common/src
RUN cd essentia-common && mvn install -DskipTests -B

# Step 2: build essentiauser.
COPY essentiauser/pom.xml essentiauser/pom.xml
RUN cd essentiauser && mvn dependency:go-offline -B

COPY essentiauser/src essentiauser/src
RUN cd essentiauser && mvn package -DskipTests -B

# ── Stage 2 — Runtime ──────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /workspace/essentiauser/target/app.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
