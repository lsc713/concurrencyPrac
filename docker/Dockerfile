FROM openjdk-17-jdk-slim-buster
WORKDIR /app
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ARG PROFILE
ENV PROFILE ${PROFILE}
ENV PORT ${PORT}
ENV PROFILE default
ENV PORT 8080
ENTRYPOINT ["java","-Dserver.port=${port}","-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]