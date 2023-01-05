FROM openjdk:19
COPY /target/*.jar oauth-service.jar
EXPOSE 8088
CMD ["java", "-jar", "oauth-service.jar"]