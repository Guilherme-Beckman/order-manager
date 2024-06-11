package com.ms.auth.utils;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.ms.auth.exceptions.auth.attempts.ExceededNumberOfAttempts;

@Component
public class AttemptManagerExponencial {
    private static final int BASE_BLOCK_DURATION_MINUTES = 1;
    private ConcurrentHashMap<String, AttemptsInfo> attemptRecords = new ConcurrentHashMap<>();

    public void checkAndUpdateAttempts(String key) {
        AttemptsInfo attemptInfo = attemptRecords.getOrDefault(key, new AttemptsInfo());
        
       
            LocalDateTime now = LocalDateTime.now();
            long blockDuration = calculateBlockDuration(attemptInfo.attempts);
            if (now.isBefore(attemptInfo.lastAttempt.plusMinutes(blockDuration))) {
            	throw new ExceededNumberOfAttempts("Try again after: " + blockDuration  +" minute(s)" );
            } else {
            	 attemptInfo.attempts++;
                 attemptInfo.lastAttempt = LocalDateTime.now();
                 attemptRecords.put(key, attemptInfo);
            }
      
       
    }

    private Long calculateBlockDuration(int attempts) {
        return BASE_BLOCK_DURATION_MINUTES * (long) Math.pow(2, attempts - 1);
    }

    private static class AttemptsInfo {
        int attempts = 0;
        LocalDateTime lastAttempt = LocalDateTime.now();
    }
}
