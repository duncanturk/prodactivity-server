FROM gradle:jdk14 as builder

# COPY .gradle /var/build/.gradle
# COPY gradle /var/build/gradle
COPY src /var/build/src
COPY build.gradle /var/build/
# COPY gradlew /var/build/
COPY settings.gradle /var/build/

WORKDIR /var/build/
RUN gradle build -x test

FROM openjdk:14

ARG UID=prodactivity
ARG VERSION=0.0.1-SNAPSHOT

RUN useradd -r --create-home --shell /bin/bash $UID
USER $UID
WORKDIR /home/$UID
COPY --from=builder /var/build/build/libs/server-${VERSION}.jar server.jar
CMD java -jar server.jar

