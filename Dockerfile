FROM openjdk:21
VOLUME /tmp
COPY target/product-api.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]