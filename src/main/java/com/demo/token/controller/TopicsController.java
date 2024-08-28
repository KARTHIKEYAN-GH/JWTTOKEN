package com.demo.token.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.token.dto.CategoryWithTopicsDto;
import com.demo.token.dto.DescriptionDTO;
import com.demo.token.dto.TopicsDTO;
import com.demo.token.model.Category;
import com.demo.token.model.ResourceNotFoundException;
import com.demo.token.model.Topics;
import com.demo.token.serviceImpl.TopicsServiceImpl;

@RestController
@RequestMapping("/api/topics")
public class TopicsController {

	private final TopicsServiceImpl topicsService;

	public TopicsController(TopicsServiceImpl topicsService) {
		super();
		this.topicsService = topicsService;
	}

	@PostMapping("/addtopics/{categoryUuid}")
	public ResponseEntity<?> addTopic(@PathVariable String categoryUuid, @RequestBody Topics topicRequest) {
		Topics topic = topicsService.addTopics(categoryUuid, topicRequest.getName(), topicRequest.getDescription());
		return ResponseEntity.ok(topic.getName() + " sTopics Addeded Sucessfully ");
	}

	@GetMapping("/getallTopicsBy/{categoryUuid}")
	public ResponseEntity<CategoryWithTopicsDto> getAllTopicsAndCategoryNamesByCategory(
			@PathVariable("categoryUuid") String categoryUuid) {
		CategoryWithTopicsDto result = topicsService.getTopicsWithCategory(categoryUuid);
		if (result == null) {
			return ResponseEntity.notFound().build(); // Handle case where no topics are found for the given category
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/getallTopics")
	public List<TopicsDTO> getAllTopics() {
		return topicsService.getAllTopics();
	}

	@PutMapping("/updateTopic/{topicsUuid}")
	public ResponseEntity<?> updateTopic(@PathVariable("topicsUuid") String topicsUuid, @RequestBody Topics updateRequest) {
	    try {
	        Optional<String> updatedTopic = topicsService.updateTopics(topicsUuid, updateRequest);
	        return ResponseEntity.ok("Updated Sucessfully "+updatedTopic);
	    } catch (ResourceNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
	    }
	}

	@GetMapping("/viewDescription/{topicsUuid}")
	public ResponseEntity<DescriptionDTO> getDescriptionAndMarkAsRead(@PathVariable("topicsUuid") String topicsUuid,
			@RequestHeader("Authorization") String token) {

		// Remove "Bearer " prefix from token if present
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		Optional<DescriptionDTO> description = topicsService.findAndMarkDescriptionAsReadByTopicsUuid(topicsUuid,
				token);

		if (description.isPresent()) {
			return ResponseEntity.ok(description.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
