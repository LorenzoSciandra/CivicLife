FROM openjdk:19-alpine
COPY /target/*.jar service-discovery.jar
EXPOSE 8761
CMD ["java", "-jar", "service-discovery.jar"]
