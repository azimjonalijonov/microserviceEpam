package com.example.EpamSpringBoot.training.dto;

import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RequestDTO {

	private String username;

	private String firstname;

	private String lastname;

	private Boolean isActive;

	private LocalDateTime DATE;

	private Number duration;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public LocalDateTime getDATE() {
		return DATE;
	}

	public void setDATE(LocalDateTime DATE) {
		this.DATE = DATE;
	}

	public Number getDuration() {
		return duration;
	}

	public void setDuration(Number duration) {
		this.duration = duration;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	private String method;

}
