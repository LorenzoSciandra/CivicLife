FROM openjdk:19-alpine
LABEL author="CivicLife"
COPY /target/*.jar external-resources-service.jar
EXPOSE 8081
CMD ["java", "-jar", "external-resources-service.jar"]
