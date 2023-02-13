
FROM openjdk:11
COPY  target/third-party-agent.jar third-party-agent.jar
COPY ../mappings/reportResponse.json /home/reportResponse.json
ENTRYPOINT ["java","-jar","third-party-agent.jar"]