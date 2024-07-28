package com.ms.auth.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String RETURN_USER_DETAILS_QUEUE = "auth_ms.return_user_details_queue";
	public static final String AUTH_USER_USER_DETAILS_DIRECT_EXCHANGE = "auth_ms.user_ms_user_details_direct_exchange";
	public static final String LOAD_USER_DETAILS_REQUEST_KEY = "load.user.details.request";

	public static final String RETURN_REGISTERED_USER_QUEUE = "auth_ms.return_registered_user_queue";
	public static final String AUTH_USER_REGISTER_USER_DIRECT_EXCHANGE = "auth_ms.register_user_direct_exchange";
	public static final String REGISTER_USER_REQUEST_KEY = "register.user.request";

	public static final String EMAIL_CODE_FANOUT_EXCHANGE = "email_code_fanout_exchange";
	public static final String USER_EMAIL_VALIDATE_FANOUT_EXCHANGE = "user_email_validate_fanout_exchange";

	public static final String EMAIL_RESET_LINK_FANOUT_EXCHANGE = "email_reset_link_fanout_exchange";
	public static final String USER_EMAIL_RESET_LINK_FANOUT_EXCHANGE = "user_email_reset_link_fanout_exchange";

	// Store
	public static final String RETURN_STORE_DETAILS_QUEUE = "auth_ms.return_store_details_queue";
	public static final String AUTH_STORE_STORE_DETAILS_DIRECT_EXCHANGE = "auth_ms.store_ms_store_details_direct_exchange";
	public static final String LOAD_STORE_DETAILS_REQUEST_KEY = "load.store.details.request";

	public static final String RETURN_REGISTERED_STORE_QUEUE = "auth_ms.return_registered_store_queue";
	public static final String AUTH_STORE_REGISTER_STORE_DIRECT_EXCHANGE = "auth_ms.register_store_direct_exchange";
	public static final String REGISTER_STORE_REQUEST_KEY = "register.store.request";
}
