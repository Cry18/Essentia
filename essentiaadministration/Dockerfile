# ── Stage 1 — Build ────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /workspace

# Step 1: install essentia-common into the container's local Maven repository.
# Copying pom.xml first allows Docker to cache the dependency-download layer
# separately from the source layer, so it only re-downloads if pom.xml changes.
COPY essentia-common/pom.xml essentia-common/pom.xml
RUN cd essentia-common && mvn dependency:go-offline -B

COPY essentia-common/src essentia-common/src
RUN cd essentia-common && mvn install -DskipTests -B

# Step 2: build essentiaadministration, which now finds essentia-common in ~/.m2.
COPY essentiaadministration/pom.xml essentiaadministration/pom.xml
RUN cd essentiaadministration && mvn dependency:go-offline -B

COPY essentiaadministration/src essentiaadministration/src
RUN cd essentiaadministration && mvn package -DskipTests -B

# ── Stage 2 — Runtime ──────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /workspace/essentiaadministration/target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
