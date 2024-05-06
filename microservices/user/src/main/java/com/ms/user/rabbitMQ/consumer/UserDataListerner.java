package com.ms.user.rabbitMQ.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.user.config.RabbitMQConfig;
import com.ms.user.infra.security.CryptoUtils;
import com.ms.user.rabbitMQ.producer.UserReturnData;
import com.ms.user.rabbitMQ.utils.UserMessageConverter;
import com.ms.user.service.UserService;
@Component
public class UserDataListerner {

    @Autowired
    public UserService userService;

    @Autowired
    public UserReturnData returnData;

    @Autowired
    public CryptoUtils cryptoUtils;

    @Autowired
    public UserMessageConverter messageConverter;

    @RabbitListener(queues = RabbitMQConfig.AUTH_QUEUE)
    public void processUserData(@Payload Message email) throws Exception {
        System.out.println("processUserData: Mensagem recebida na fila AUTH_QUEUE.");

        // Verificar se a mensagem não é null
        if (email != null) {
            System.out.println("Mensagem recebida: " + email);

            // Extrair o email da mensagem
            var emailP = new String(email.getBody());
            System.out.println("Email extraído: " + emailP);

            // Criptografar o email
            var encryptedEmail = this.cryptoUtils.encrypt(emailP);
            System.out.println("Email criptografado: " + encryptedEmail);

            // Buscar usuário pelo email criptografado
            var findedUser = this.userService.getUserByEmail(encryptedEmail);
            System.out.println("Usuário encontrado: " + (findedUser != null ? findedUser.toString() : "Nenhum usuário encontrado"));

            // Converter o usuário para mensagem de resposta
            var userBytes = this.messageConverter.convertUserToMessage(findedUser, email.getMessageProperties().getHeader("correlationId"));
            System.out.println("Usuário convertido para bytes de resposta: "+userBytes);

            // Enviar a resposta de volta para o RabbitMQ
            
            this.returnData.returnUserData(userBytes);
            System.out.println("Resposta enviada para o RabbitMQ.");
        } else {
            System.out.println("Mensagem recebida é null. Retornando dados nulos.");
            this.returnData.returnUserData(null);
        }
    }
}
