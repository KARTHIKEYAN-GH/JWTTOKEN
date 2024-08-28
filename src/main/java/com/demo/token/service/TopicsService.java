package com.demo.token.service;

import java.util.List;
import java.util.Optional;

import com.demo.token.dto.CategoryWithTopicsDto;
import com.demo.token.dto.DescriptionDTO;
import com.demo.token.dto.TopicsDTO;
import com.demo.token.model.Topics;

public interface TopicsService {
	 Topics addTopics(String categoryUuid, String name,String description);
	
	 CategoryWithTopicsDto getTopicsWithCategory(String categoryUuid);
	
	 TopicsDTO convertsToDTO(Topics topics);
	
	 List<TopicsDTO> getAllTopics();
	
	 Optional<String> updateTopics(String topicsUuid ,Topics topics);
	
	 Optional<DescriptionDTO> findAndMarkDescriptionAsReadByTopicsUuid(String topicsUuid,String token);

}
