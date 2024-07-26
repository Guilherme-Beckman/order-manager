FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/global-config-0.0.1-SNAPSHOT.jar .

# Expor a porta que o microservice vai usar
EXPOSE 8888

# Comando para rodar a aplicação
CMD ["java", "-jar", "global-config-0.0.1-SNAPSHOT.jar"]
