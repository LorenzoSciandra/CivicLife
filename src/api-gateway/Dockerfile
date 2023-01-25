FROM openjdk:19-alpine
LABEL author="CivicLife"
COPY /target/*.jar api-gateway.jar
EXPOSE 8080
CMD ["java", "-jar", "api-gateway.jar"]
