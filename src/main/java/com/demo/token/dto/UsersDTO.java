package com.demo.token.dto;

import com.demo.token.model.Users.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UsersDTO {
	
	private String uuid;
	private String name;
	private String email;
	private String userName;
	private Role role;
	private boolean isActive;

	
	public UsersDTO() {
		super();
	}
	public UsersDTO(String uuid, String name, String email, String userName, Role role, Boolean isActive) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.email = email;
		this.userName = userName;
		this.role = role;
		this.isActive = isActive;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
