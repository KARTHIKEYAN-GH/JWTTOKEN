package com.demo.token.model;

import java.time.LocalDateTime;

import com.demo.token.model.Users.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//@Data
//@AllArgConstructor

@Entity
public class TopicsReadStatus {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "topics_id")
	private Topics topics;
	
	@ManyToOne
	@JoinColumn(name="users_id")
	private Users users;
	
	private String userName;
	
	public String getUser_uuid() {
		return user_uuid;
	}

	public void setUser_uuid(String user_uuid) {
		this.user_uuid = user_uuid;
	}

	public Role getRole() {
		return role;
	}


	private String user_uuid;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	public void setRole(Role role) {
		this.role = role;		
	}
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	private LocalDateTime readAt;

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Topics getTopics() {
		return topics;
	}


	public void setTopics(Topics topics) {
		this.topics = topics;
	}


	public Users getUsers() {
		return users;
	}


	public void setUsers(Users users) {
		this.users = users;
	}


	public LocalDateTime getReadAt() {
		return readAt;
	}


	public void setReadAt(LocalDateTime readAt) {
		this.readAt = readAt;
	}


	
}
