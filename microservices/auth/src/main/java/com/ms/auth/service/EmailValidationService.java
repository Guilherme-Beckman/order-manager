package com.ms.auth.service;

import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.auth.dto.ValidateEmailDTO;
import com.ms.auth.exceptions.auth.email.code.EmailAlreadyBeenVerifiedException;
import com.ms.auth.infra.security.TokenService;
import com.ms.auth.rabbitMQ.producer.EmailCodeProducer;
import com.ms.auth.utils.MessageUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailValidationService {
	@Autowired
	private TokenService tokenService;
    @Autowired
    private MessageUtils messageUtils;
    private ConcurrentHashMap<String, CompletableFuture<String>> pendingResponses = new ConcurrentHashMap<>();
	@Autowired
	private EmailCodeProducer emailCodeProducer; 
	private int attempts;
	public String sendCode(HttpServletRequest request) {
	    var token = this.tokenService.recoverToken(request);
	    System.out.println("sendCode method in EmailValidationService: Token recovered");
	    
	    var userInfos = this.tokenService.getTokenInformations(token);
	    System.out.println("sendCode method in EmailValidationService: Token information retrieved");
	    
	    Boolean isEnable = userInfos.getClaim("enable").asBoolean();
	    System.out.println("sendCode method in EmailValidationService: User enabled status retrieved: " + isEnable);
	    
	    String email = userInfos.getSubject();
	    System.out.println("sendCode method in EmailValidationService: User email retrieved: " + email);
	    
	    if (isEnable) {
	        System.out.println("sendCode method in EmailValidationService: Email already verified, throwing exception");
	        throw new EmailAlreadyBeenVerifiedException();
	    }
	    
	    String code = this.generateCode();
	    System.out.println("sendCode method in EmailValidationService: Generated code - " + code);
	    
	    CompletableFuture<String> responseFuture = new CompletableFuture<>();
	    System.out.println("sendCode method in EmailValidationService: Created CompletableFuture for email: " + email);
	    
	    pendingResponses.put(code, responseFuture);
	    System.out.println("sendCode method in EmailValidationService: Added CompletableFuture to pendingResponses map for email: " + email);
	    
	    Message message = this.messageUtils.createMessage(email, code);
	    System.out.println("sendCode method in EmailValidationService: Message created for email: " + email);
	    
	    System.out.println("sendCode method in EmailValidationService: Sending email code for email - " + email);
	    this.emailCodeProducer.produceEmailCode(message);
	    
	    try {
	        responseFuture.get(60000, TimeUnit.MILLISECONDS);
	        System.out.println("sendCode method in EmailValidationService: Email validated with success for email - " + email);
	        return "Email validated with success";
	    } catch (Exception e) {
	        System.out.println("sendCode method in EmailValidationService: Error occurred while validating email for email - " + email);
	        return null;
	    } finally {
	        pendingResponses.remove(code);
	        System.out.println("sendCode method in EmailValidationService: Removed CompletableFuture from pendingResponses map for code: " + code);
	    }
	}

	private String generateCode() {
		SecureRandom secureRandom = new SecureRandom();
		int code = secureRandom.nextInt(900000)+100000;
		return String.valueOf(code);
	}
	
	
	public String validateEmailCode(ValidateEmailDTO emailCodeDTO, HttpServletRequest request) {
	    var token = this.tokenService.recoverToken(request);
	    System.out.println("validateEmailCode method in EmailValidationService: Token recovered");
	    
	    var userInfos = this.tokenService.getTokenInformations(token);
	    System.out.println("validateEmailCode method in EmailValidationService: Token information retrieved");
	    
	    Boolean isEnable = userInfos.getClaim("enable").asBoolean();
	    System.out.println("validateEmailCode method in EmailValidationService: User enabled status retrieved: " + isEnable);
	    
	    String email = userInfos.getSubject();
	    System.out.println("validateEmailCode method in EmailValidationService: User email retrieved: " + email);
	    
	    if (isEnable) {
	        System.out.println("validateEmailCode method in EmailValidationService: Email already verified, throwing exception");
	        throw new EmailAlreadyBeenVerifiedException();
	    }
	    
	    CompletableFuture<String> responseFuture = pendingResponses.get(emailCodeDTO.emailCode());
	    System.out.println("validateEmailCode method in EmailValidationService: Retrieved CompletableFuture from pendingResponses map for email: " + email);
	        if (pendingResponses!=null) {
	            System.out.println("validateEmailCode method in EmailValidationService: Email validated successfully for email - " + email);
	            responseFuture.complete(emailCodeDTO.emailCode());
	            return "Email validated successfully";
	        } else {
	            System.out.println("validateEmailCode method in EmailValidationService: Email validation failed for email - " + email);
	        }
	    
	    return email;
	}
}

