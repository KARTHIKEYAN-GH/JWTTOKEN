package com.demo.token.model;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

//@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "userName" }) })

public class Users  {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
	    @Column(nullable = false, unique = true, updatable = false)
		private String uuid=UUID.randomUUID().toString();
		
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		private String name;
		

	    @Email  
		private String email;
		
		private String phoneNumber;
		private String userName;
		private String password;
		private Boolean isActive;
		
		public enum Role {
			ADMIN, TRAINEE, ATTENDEE
		}

		@Enumerated(EnumType.STRING)
		private Role role;
		
		//@ManyToMany(mappedBy="users")
		//private Set<TopicsReadStatus> readStatus;
		
		@CreatedDate
	    @Column(nullable = false, updatable = false)
	    private LocalDateTime createdAt;
		
		@LastModifiedDate
	    private LocalDateTime updatedAt;

	//	public Set<TopicsReadStatus> getReadStatus() {
	//		return readStatus;
	//	}
	// void setReadStatus(Set<TopicsReadStatus> readStatus) {
	//		this.readStatus = readStatus;
	//	}
		public boolean isActive() {
			return isActive;
		}
		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
		
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
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
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		
	    /**
		 * @return the createdAt
		 */
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		/**
		 * @param createdAt the createdAt to set
		 */
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		/**
		 * @return the updatedAt
		 */
		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}
		/**
		 * @param updatedAt the updatedAt to set
		 */
		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
		public Boolean getIsActive() {
			return isActive;
		}
		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}


}
