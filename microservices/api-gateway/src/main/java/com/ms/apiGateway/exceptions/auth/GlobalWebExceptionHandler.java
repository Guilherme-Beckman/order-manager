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
@Order(-2)
public class GlobalWebExceptionHandler implements WebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

		if (exchange.getResponse().isCommitted()) {
			return Mono.error(ex);
		}
		HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
		String type = "about:blank"; // Default type conforme a RFC 7807
		String title = "Internal Server Error";
		String detail = ex.getMessage();

		if (ex instanceof AuthException) {

			exchange.getResponse().setStatusCode(status);
			exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);
			DataBuffer dataBuffer = buildProblemDetail(exchange, (AuthException) ex);
			return exchange.getResponse().writeWith(Mono.just(dataBuffer));
		} else if (ex instanceof ResponseStatusException) {
			status = ((ResponseStatusException) ex).getStatusCode();
			type = "https://example.com/" + status.value();
			title = "HTTP Status " + status.value();
			exchange.getResponse().setStatusCode(status);
			exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);

			DataBuffer dataBuffer = buildProblemDetail(exchange, status, type, title, detail);

			return exchange.getResponse().writeWith(Mono.just(dataBuffer));
		}
		return null;

	}

	private DataBuffer buildProblemDetail(ServerWebExchange exchange, AuthException ex) {
		try {
			ProblemDetail problemDetail = ex.toProblemDetail();
			ObjectMapper objectMapper = new ObjectMapper();
			byte[] bytes = objectMapper.writeValueAsBytes(problemDetail);

			return exchange.getResponse().bufferFactory().wrap(bytes);
		} catch (Exception e) {
			byte[] fallback = ("{\"title\":\"Internal Server Error\",\"status\":500,\"detail\":\"An error occurred while processing the error response.\"}")
					.getBytes();
			return exchange.getResponse().bufferFactory().wrap(fallback);// faz a mesma coisa, mas com error padrão
		}
	}

	private DataBuffer buildProblemDetail(ServerWebExchange exchange, HttpStatusCode status, String type, String title,
			String detail) {
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
			byte[] fallback = ("{\"title\":\"Internal Server Error\",\"status\":500,\"detail\":\"An error occurred while processing the error response.\"}")
					.getBytes();
			return exchange.getResponse().bufferFactory().wrap(fallback);
		}
	}
}
