FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/user-0.0.1-SNAPSHOT.jar .

# Expor a porta que o microservice vai usar
EXPOSE 8082

# Comando para rodar a aplicação
CMD ["java", "-jar", "user-0.0.1-SNAPSHOT.jar"]
