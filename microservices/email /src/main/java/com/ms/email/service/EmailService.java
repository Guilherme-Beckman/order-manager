package com.ms.email.service;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	

	public void sendEmailCode(Message message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String email = new String(message.getBody());
		mailMessage.setTo(email);
		mailMessage.setSubject("Code ");
		mailMessage.setText("Your code is: " + message.getMessageProperties().getCorrelationId());

		javaMailSender.send(mailMessage);
	}

}
