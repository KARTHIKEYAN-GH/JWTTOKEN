package com.demo.token.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class CatregoryDTO {

	private String uuid;
	private String name;
	private String createdBY;
	
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
	public String getCreatedBY() {
		return createdBY;
	}
	public void setCreatedBY(String createdBY) {
		this.createdBY = createdBY;
	}
	

	public CatregoryDTO() {
		super();
	}
	public CatregoryDTO(String uuid, String name, String createdBY) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.createdBY = createdBY;
	}
	
}
