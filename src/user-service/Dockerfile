FROM openjdk:19-alpine
LABEL author="CivicLife"
COPY /target/*.jar user-service.jar
EXPOSE 8083
CMD ["java", "-jar", "user-service.jar"]
