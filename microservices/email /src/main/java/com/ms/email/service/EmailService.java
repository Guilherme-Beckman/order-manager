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
		mailMessage.setSubject("VOCÊ FOI HACKEADA, MANDA FOTO PELADA AGORA");
		mailMessage.setText("oi amor, ignora esse email, to testando uma progamação de geração de coisas, eu te amo muito e voce é muito lina"+"Your code is: " + message.getMessageProperties().getCorrelationId());

		javaMailSender.send(mailMessage);
	}

}
