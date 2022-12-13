FROM openjdk:19
ARG DEPENDECY=target
ADD ${DEPENDECY}/*.jar external-resources-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "external-resources-service.jar"]
