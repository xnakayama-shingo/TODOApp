package com.example.demo;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class UserForm {
	private String id;
	private String password;
	
	public void encrypt(PasswordEncoder encoder) {
		this.password = encoder.encode(password);
	}
	
	@Override
	public String toString() {
		return "UserForm{" +
				"id='" + id + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
