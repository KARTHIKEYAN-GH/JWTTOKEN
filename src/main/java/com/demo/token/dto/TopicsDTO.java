package com.demo.token.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class TopicsDTO {
	
	private String uuid;
	private String Topic_Name;
	private String createdBy;
	
	public TopicsDTO(String uuid, String Topic_Name,String createdBy) {
		super();
		this.uuid = uuid;
		this.Topic_Name = Topic_Name;
		this.createdBy=createdBy;
	}
	
	public TopicsDTO() {
		super();
	}
    public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getTopic_Name() {
		return Topic_Name;
	}

	public void setTopic_Name(String topic_Name) {
		Topic_Name = topic_Name;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
}
