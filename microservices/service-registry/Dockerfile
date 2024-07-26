FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/service-registry-0.0.1-SNAPSHOT.jar .

# Expor a porta que o microservice vai usar
EXPOSE 8761

# Comando para rodar a aplicação
CMD ["java", "-jar", "service-registry-0.0.1-SNAPSHOT.jar"]
