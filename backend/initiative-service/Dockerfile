FROM openjdk:19-alpine
LABEL author="CivicLife"
COPY /target/*.jar initiative-service.jar
EXPOSE 8082
CMD ["java", "-jar", "initiative-service.jar"]
