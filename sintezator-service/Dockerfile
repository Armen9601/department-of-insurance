
FROM openjdk:11
COPY  target/sintezator-service.jar sintezator-service.jar
COPY src/main/resources/sintezator.json /home/sintezator.json
ENTRYPOINT ["java","-jar","sintezator-service.jar"]