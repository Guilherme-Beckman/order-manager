FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar .

# Expor a porta que o microservice vai usar
EXPOSE 8989

# Comando para rodar a aplicação
CMD ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]
