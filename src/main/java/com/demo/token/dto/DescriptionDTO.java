package com.demo.token.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class DescriptionDTO {
	
	private String description;

	public DescriptionDTO() {
		super();
	}


	public DescriptionDTO(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
