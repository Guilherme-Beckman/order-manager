package com.ms.user.infra.security;
import java.util.Base64;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.exceptions.CryptographyException;
@Component
public class CryptoUtils {

    private final SecretKey secretKey;
    public CryptoUtils(@Value("${spring.security.secret-key}") String encodedKey) {
        this.secretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedKey), "AES");
    }

    public String encrypt(String data)  {
    	if(data == null) return "null";
    	try {
    		    Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    	}catch(Exception e){
    		throw new CryptographyException("Error while encrypt data", e.getCause());
    	}
    
    }

    public String decrypt(String encryptedData)  {
    	if(encryptedData == null) return "null";
    	try {
    		  Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    	}
    	catch(Exception e) {
    		throw new CryptographyException("Error while decrypt data", e.getCause());
    	}
      
    }
}
