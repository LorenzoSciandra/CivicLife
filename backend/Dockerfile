FROM openjdk:19
RUN echo $VERSION
#RUN addgroup -S spring && adduser -S spring -G spring
VOLUME ["/var/www", "/var/log/apache2", "/etc/apache2"]
EXPOSE 8080
ARG DEPENDENCY=target
ADD ${DEPENDENCY}/*.jar civiclife.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/civiclife.jar"]