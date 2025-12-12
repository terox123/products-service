FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY target/product-service-0.0.1-SNAPSHOT.jar p-service.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "p-service.jar"]