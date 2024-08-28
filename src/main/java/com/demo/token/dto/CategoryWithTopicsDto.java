package com.demo.token.dto;

import java.util.List;
import java.util.UUID;

public class CategoryWithTopicsDto {
	
	private Long categoryId;
	private String categoryUuid=UUID.randomUUID().toString();
	private String categoryName;
	private List<String> topics;
	
	public String getUuid() {
		return categoryUuid;
	}

	public void setUuid(String uuid) {
		this.categoryUuid = uuid;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	
	
	public CategoryWithTopicsDto(Long categoryId, String categoryName, List<String> topics,String categoryUuid) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.topics = topics;
		this.categoryUuid=categoryUuid;
	}

	public CategoryWithTopicsDto(String categoryUuid, String categoryName, List<String> topics) {
		this.categoryName = categoryName;
		this.topics = topics;
		this.categoryUuid=categoryUuid;	}

	
}
