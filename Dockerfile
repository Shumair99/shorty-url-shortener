FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} shorty.jar
ENTRYPOINT ["java","-jar","/shorty.jar"]