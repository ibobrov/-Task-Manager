FROM openjdk:21-jdk-slim
LABEL authors="ibobrov"
COPY target/task-tracker-backend-0.0.1.jar task-tracker-backend-0.0.1.jar
ENTRYPOINT ["java","-jar","/task-tracker-backend-0.0.1.jar"]
EXPOSE 8080