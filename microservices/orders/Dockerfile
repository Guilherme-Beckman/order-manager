FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/orders-0.0.1-SNAPSHOT.jar .

# Expor a porta que o microservice vai usar
EXPOSE 8086

# Comando para rodar a aplicação
CMD ["java", "-jar", "orders-0.0.1-SNAPSHOT.jar"]
