FROM sapmachine:latest
ADD target/docker-rest-test.jar docker-rest-test.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "docker-rest-test.jar"]