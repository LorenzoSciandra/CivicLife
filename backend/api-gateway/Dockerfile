FROM openjdk:19-alpine
COPY /target/*.jar api-gateway.jar
EXPOSE 8080
CMD ["java", "-jar", "api-gateway.jar"]
