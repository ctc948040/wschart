FROM openjdk:8-jdk-alpine
ENV HOME=/app
ARG PORT=8081
ARG SERVICE=wschart
ENV PORT=${PORT}
ENV SERVICE="${SERVICE}"

ADD ./target/*.jar ${HOME}/app.jar
ADD ./${SERVICE}-entrypoint.sh ${HOME}/${SERVICE}-entrypoint.sh
WORKDIR ${HOME}
EXPOSE ${PORT}/tcp
EXPOSE 8000/tcp
ENTRYPOINT sh ./${SERVICE}-entrypoint.sh