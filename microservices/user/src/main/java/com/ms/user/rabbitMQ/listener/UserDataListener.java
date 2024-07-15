package com.ms.user.rabbitMQ.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.user.config.RabbitMQConfig;
import com.ms.user.infra.security.CryptoUtils;
import com.ms.user.rabbitMQ.producer.UserReturnDataProducer;
import com.ms.user.rabbitMQ.utils.UserMessageConverter;
import com.ms.user.service.UserService;

@Component
public class UserDataListener {

	@Autowired
	public UserService userService;

	@Autowired
	public UserReturnDataProducer returnData;

	@Autowired
	public CryptoUtils cryptoUtils;

	@Autowired
	public UserMessageConverter messageConverter;

	@RabbitListener(queues = RabbitMQConfig.LOAD_USER_DETAILS_QUEUE)
	public void processUserData(@Payload Message email) {

		if (email != null) {
			String emailP = new String(email.getBody());
			emailP = emailP.replace("\"", "");
			var encryptedEmail = this.cryptoUtils.encrypt(emailP);
			var findedUser = this.userService.getUserByEmail(encryptedEmail);

			if (findedUser != null) {
				var userBytes = this.messageConverter.convertUserToMessage(findedUser,
						email.getMessageProperties().getCorrelationId());
				System.out.println(userBytes);
				this.returnData.returnUserData(userBytes);
			}
		}
	}
}
