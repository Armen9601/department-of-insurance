

FROM openjdk:11
COPY  target/request-service.jar request-service.jar
COPY src/main/resources/sintezator.json /home/sintezator.json
#EXPOSE 11210
ENTRYPOINT ["java","-jar","request-service.jar"]