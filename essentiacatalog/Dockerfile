# ── Stage 1 — Build ────────────────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /workspace

# Step 1: install essentia-common into the container's local Maven repository.
COPY essentia-common/pom.xml essentia-common/pom.xml
RUN cd essentia-common && mvn dependency:go-offline -B

COPY essentia-common/src essentia-common/src
RUN cd essentia-common && mvn install -DskipTests -B

# Step 2: build essentiacatalog.
COPY essentiacatalog/pom.xml essentiacatalog/pom.xml
RUN cd essentiacatalog && mvn dependency:go-offline -B

COPY essentiacatalog/src essentiacatalog/src
RUN cd essentiacatalog && mvn package -DskipTests -B

# ── Stage 2 — Runtime ──────────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /workspace/essentiacatalog/target/app.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
