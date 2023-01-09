FROM openjdk:19-alpine
COPY /target/*.jar user-service.jar
EXPOSE 8083
CMD ["java", "-jar", "user-service.jar"]
