FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/email-0.0.1-SNAPSHOT.jar .

# Expor a porta que o microservice vai usar
EXPOSE 8083

# Comando para rodar a aplicação
CMD ["java", "-jar", "email-0.0.1-SNAPSHOT.jar"]
