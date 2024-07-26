FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/auth-0.0.1-SNAPSHOT.jar .

# Expor a porta que o microservice vai usar
EXPOSE 8081

# Comando para rodar a aplicação
CMD ["java", "-jar", "auth-0.0.1-SNAPSHOT.jar"]
