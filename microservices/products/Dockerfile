FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/products-0.0.1-SNAPSHOT.jar .

# Expor a porta que o microservice vai usar
EXPOSE 8084

# Comando para rodar a aplicação
CMD ["java", "-jar", "products-0.0.1-SNAPSHOT.jar"]
