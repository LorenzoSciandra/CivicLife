FROM openjdk:19-alpine
LABEL author="CivicLife"
COPY /target/*.jar vote-service.jar
EXPOSE 8084
CMD ["java", "-jar", "vote-service.jar"]
