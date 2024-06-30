package com.ms.api_gateway.exceptions.auth;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import com.ms.api_gateway.exceptions.auth.token.TokenNotValidException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(-2)
public class GlobalWebExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String type = "about:blank"; // Default type conforme a RFC 7807
        String title = "Internal Server Error";
        String detail = ex.getMessage();

        // Identificar o tipo de exceção e ajustar a resposta
        if (ex instanceof TokenNotValidException) {
            status = HttpStatus.UNAUTHORIZED;
            type = "https://example.com/token-not-valid";
            title = "Token Not Valid";
            detail = "The provided token is invalid or expired.";
        } else if (ex instanceof ResponseStatusException) {
            status = (HttpStatus) ((ResponseStatusException) ex).getStatusCode();
            type = "https://example.com/" + status.value();
            title = "HTTP Status " + status.value();
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        // Criar o corpo de resposta em formato ProblemDetail
        DataBuffer dataBuffer = buildProblemDetail(exchange, status, type, title, detail);

        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    private DataBuffer buildProblemDetail(ServerWebExchange exchange, HttpStatus status, String type, String title, String detail) {
        try {
            // Criar o objeto ProblemDetail conforme RFC 7807
            Map<String, Object> problemDetail = new HashMap<>();
            problemDetail.put("type", type);
            problemDetail.put("title", title);
            problemDetail.put("status", status.value());
            problemDetail.put("detail", detail);
            problemDetail.put("instance", exchange.getRequest().getPath().toString());

            // Converter para JSON
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(problemDetail);
            
            // Retornar o buffer de dados
            return exchange.getResponse().bufferFactory().wrap(bytes);
        } catch (Exception e) {
            // Tratamento de erro ao criar o corpo de resposta
            byte[] fallback = ("{\"title\":\"Internal Server Error\",\"status\":500,\"detail\":\"An error occurred while processing the error response.\"}").getBytes();
            return exchange.getResponse().bufferFactory().wrap(fallback);
        }
    }
}
