package com.example.EpamSpringBoot.config.bruteforceprotect;

import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DefaultBruteForceProtectorSrvice {

	private int maxFailLimit = 3;

	@Autowired
	UserRepository userRepository;

	int cacheMax;

	public void registerFailedLogin(String username) {
		User user = userRepository.findUserByUsername(username);
		if (user != null && !user.getLoginDisabled()) {
			int failedLogins = user.getFailedAttempts();
			if (maxFailLimit < failedLogins + 1) {
				user.setLoginDisabled(true);
				user.setLockTime(LocalDateTime.now());
			}
			else {
				user.setFailedAttempts(failedLogins + 1);
			}
			userRepository.save(user);
		}

	}

	public void resetBruteForceAttack(String username) {
		User user = userRepository.findUserByUsername(username);
		if (user != null) {
			user.setFailedAttempts(0);
			user.setLoginDisabled(false);
			user.setLockTime(null);
			userRepository.save(user);
		}

	}

}
