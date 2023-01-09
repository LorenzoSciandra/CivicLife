FROM openjdk:19-alpine
COPY /target/*.jar external-resources-service.jar
EXPOSE 8081
CMD ["java", "-jar", "external-resources-service.jar"]
