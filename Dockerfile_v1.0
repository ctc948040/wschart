# syntax=docker/dockerfile:experimental
FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
#ADD /home/vagrant/.m2 /root/

RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests

FROM openjdk:8-jdk-alpine
#VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target
ENV HOME=/app
ARG PORT=8081
ARG SERVICE=wschart
ENV PORT=${PORT}
ENV SERVICE="${SERVICE}"

#ADD ./target/*.jar ${HOME}/app.jar
COPY --from=build ${DEPENDENCY}/*.jar ${HOME}/app.jar

ADD ./${SERVICE}-entrypoint.sh ${HOME}/${SERVICE}-entrypoint.sh
WORKDIR ${HOME}
EXPOSE ${PORT}/tcp
EXPOSE 8000/tcp
ENTRYPOINT sh ./${SERVICE}-entrypoint.sh