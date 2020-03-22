package com.stevenstier.validation.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class Login {
	@NotBlank(message = "A email address is required.")
	@Email(message = "A valid email address is required.")
	private String email;

	@NotBlank(message = "A password is required.")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
