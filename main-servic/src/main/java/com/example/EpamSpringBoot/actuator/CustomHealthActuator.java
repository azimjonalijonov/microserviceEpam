package com.example.EpamSpringBoot.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthActuator implements HealthIndicator {

	@Override
	public Health health() {
		return Health.up().build();
	}

}
