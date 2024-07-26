FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/stores-0.0.1-SNAPSHOT.jar .

# Expor a porta que o microservice vai usar
EXPOSE 8085

# Comando para rodar a aplicação
CMD ["java", "-jar", "stores-0.0.1-SNAPSHOT.jar"]
