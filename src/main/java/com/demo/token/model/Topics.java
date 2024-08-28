package com.demo.token.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class Topics {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @Column(nullable = false, unique = true, updatable = false)
	private String uuid=UUID.randomUUID().toString();

	private String name;
	
	private String description;
	
	//private boolean isRead;
	
	//private String readBy;
	
	@OneToMany(mappedBy = "topics")
    private Set<TopicsReadStatus> readStatuses;


	@ManyToOne
	@JoinColumn(name="category_id", updatable=false )
	private Category category;
	
	//@Column(name="category_id")
	//private Long categoryId;
	
	@CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
	
	@LastModifiedDate
    private LocalDateTime updatedAt;
	
	@CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}
