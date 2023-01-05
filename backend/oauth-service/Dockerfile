FROM openjdk:19-alpine
COPY /target/*.jar oauth-service.jar
EXPOSE 8088
CMD ["java", "-jar", "oauth-service.jar"]
