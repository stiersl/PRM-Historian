package com.stevenstier.validation.model;


import java.time.LocalDate;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Registration {

	@Size(max = 20, message = "Firstname cannot exceed 20 characters!")
	@NotBlank(message = "You must enter a Firstname!")
	private String firstName;

	@Size(max = 20, message = "Lastname cannot exceed 20 characters!")
	@NotBlank(message = "You must enter a LastName!")
	private String lastName;

	@Email(message = "Email address is invalid!")
	@NotBlank(message = "Email address required!")
	private String email;

	@Email(message = "Confirmation Email address is invalid!")
	@NotBlank(message = "You must enter a confirmation Email!")
	private String emailConfirmation;

	@Size(min = 8, message = "Password must be greater than 8 characters!")
	@NotBlank(message = "You must enter a password!")
	private String password;

	@NotBlank(message = "You must enter a password confirmation!")
	private String passwordConfirmation;

	private String phoneNumber;

	@NotNull(message = "You must enter a birth date!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthdate;

	@AssertTrue(message = "emails do not match!")
	public boolean isEmailMatching() {
		return email != null && email.equals(emailConfirmation);
	}

	@AssertTrue(message = "Passwords do not match!")
	public boolean isPasswordMatching() {
		return password != null && password.equals(passwordConfirmation);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailConfirmation() {
		return emailConfirmation;
	}

	public void setEmailConfirmation(String emailConfirmation) {
		this.emailConfirmation = emailConfirmation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
