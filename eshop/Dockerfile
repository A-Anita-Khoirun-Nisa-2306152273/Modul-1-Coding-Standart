FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /src
COPY . .

# Project Gradle ada di folder "eshop"
WORKDIR /src/eshop
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar -x test

FROM docker.io/library/eclipse-temurin:21-jre-alpine AS runner

ARG USERNAME=anitakny
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USERNAME} && \
    adduser -S -u ${USER_UID} -G ${USERNAME} ${USERNAME}

USER ${USERNAME}
WORKDIR /opt/advpro

EXPOSE 8080

COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/eshop/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]