package com.demo.token.model;

public class AuthenticationResponse {
	private String token;

	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken()
	{
		return token;
	}
}
