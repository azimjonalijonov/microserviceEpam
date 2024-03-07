package com.example.EpamSpringBoot.config.bruteforceprotect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class FailureListeneer implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
	DefaultBruteForceProtectorSrvice defaultBruteForceProtectorSrvice;

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		String username = event.getAuthentication().getName();
		defaultBruteForceProtectorSrvice.registerFailedLogin(username);
		System.out.println("username_______________________________________" + username);
	}

//	@Override
//	public boolean supportsAsyncExecution() {
//		return ApplicationListener.super.supportsAsyncExecution();
//	}

}
