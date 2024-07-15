package com.ms.apiGateway.exceptions.auth;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

@Component
@Order(-2)//indica a prioridade de excução 
public class GlobalWebExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void>//fluxo assincrono que retornara apenas um valor ou lancara error
    handle(ServerWebExchange exchange//requisição no ambiente solicitação e resposta
    		, Throwable ex) {

        if (exchange.getResponse().isCommitted()//metodo que verifica se a resposta já foi enviada
        		) {
            return Mono.error(ex);
        }
        //definição de valores padrão
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        String type = "about:blank"; // Default type conforme a RFC 7807
        String title = "Internal Server Error";
        String detail = ex.getMessage();

        // Identificar o tipo de exceção e ajustar a resposta
        if (ex instanceof AuthException) {

            exchange.getResponse().setStatusCode(status);//seta a resposta como o tipo definido 
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);//ajusta para pb

            DataBuffer dataBuffer //area temporaria de memoria que é usada para transmitir para transmitir informações
            = buildProblemDetail(exchange, (AuthException) ex);
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        } else if (ex instanceof ResponseStatusException) {
            status =  ((ResponseStatusException) ex).getStatusCode();
            type = "https://example.com/" + status.value();
            title = "HTTP Status " + status.value();
            exchange.getResponse().setStatusCode(status);//seta a resposta como o tipo definido 
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);//ajusta para pb

            DataBuffer dataBuffer //area temporaria de memoria que é usada para transmitir para transmitir informações
            = buildProblemDetail(exchange, status, type, title, detail);

            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        }
		return null;

     
    }

    private DataBuffer buildProblemDetail(ServerWebExchange exchange,AuthException ex ) {
        try {
            ProblemDetail problemDetail =  ex.toProblemDetail();
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(problemDetail);
            
            return exchange.getResponse()
            		.bufferFactory()//retorna a fabrica de buffer relacionada a resposta
            		.wrap(bytes);//"enrola" bytes dentro do buffer
        } catch (Exception e) {
            byte[] fallback = ("{\"title\":\"Internal Server Error\",\"status\":500,\"detail\":\"An error occurred while processing the error response.\"}").getBytes();
            return exchange.getResponse().bufferFactory().wrap(fallback);//faz a mesma coisa, mas com error padrão
        }
    }
    private DataBuffer buildProblemDetail(ServerWebExchange exchange, HttpStatusCode status, String type, String title, String detail) {
        try {
            ProblemDetail problemDetail = ProblemDetail.forStatus(status);
            problemDetail.setTitle(title);
            problemDetail.setStatus(status.value());
            problemDetail.setDetail(detail);
            problemDetail.setInstance(exchange.getRequest().getURI());

            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(problemDetail);
            
            return exchange.getResponse().bufferFactory().wrap(bytes);
        } catch (Exception e) {
            byte[] fallback = ("{\"title\":\"Internal Server Error\",\"status\":500,\"detail\":\"An error occurred while processing the error response.\"}").getBytes();
            return exchange.getResponse().bufferFactory().wrap(fallback);
        }
    }
}
