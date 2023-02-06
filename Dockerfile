# --- WIREMOCK --
# --- WIREMOCK --
FROM openjdk:17
COPY external-lib/wiremock-1.33-standalone.jar wiremock-1.33-standalone.jar
COPY mappings/mapping.json /mappings/reportResponse.json
COPY mappings/reportResponse.json __files/reportResponse.json

ENTRYPOINT ["java","-jar","wiremock-1.33-standalone.jar","--port","15500"]
