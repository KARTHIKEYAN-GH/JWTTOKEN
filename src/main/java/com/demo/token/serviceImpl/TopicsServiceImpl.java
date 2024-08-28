package com.demo.token.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.token.dto.CategoryWithTopicsDto;
import com.demo.token.dto.DescriptionDTO;
import com.demo.token.dto.TopicsDTO;
import com.demo.token.exception.TopicNotFoundException;
import com.demo.token.model.Category;
import com.demo.token.model.ResourceNotFoundException;
import com.demo.token.model.Topics;
import com.demo.token.model.TopicsReadStatus;
import com.demo.token.model.Users;
import com.demo.token.model.Users.Role;
import com.demo.token.repo.CategoryRepository;
import com.demo.token.repo.TopicReadStatusRepository;
import com.demo.token.repo.TopicsRepository;
import com.demo.token.repo.UserRepository;
import com.demo.token.service.TopicsService;

@Service
public class TopicsServiceImpl implements TopicsService {
	@Autowired
	private final JwtService jwtservice;
	private final TopicsRepository topicsRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	private final TopicReadStatusRepository topicReadStatusRepository;

	public TopicsServiceImpl(TopicsRepository topicsRepository, CategoryRepository categoryRepository,
			JwtService jwtservice, UserRepository userRepository, TopicReadStatusRepository topicReadStatusRepository) {
		super();
		this.jwtservice = jwtservice;
		this.categoryRepository = categoryRepository;
		this.topicsRepository = topicsRepository;
		this.userRepository = userRepository;
		this.topicReadStatusRepository = topicReadStatusRepository;
	}

	public Topics addTopics(String categoryUuid, String name, String description) {
		Category category = categoryRepository.findByUuid(categoryUuid)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		Topics topics = new Topics();
		topics.setName(name);
		topics.setDescription(description);
		topics.setCategory(category);
		return topicsRepository.save(topics);
	}

	public CategoryWithTopicsDto getTopicsWithCategory(String categoryUuid) {
		List<Object[]> results = topicsRepository.findTopicAndCategoryNameByCategoryUuid(categoryUuid);
		if (results.isEmpty()) {
			return null; // Handle case where no topics are found for the given category
		}

		String categoryName = (String) results.get(0)[2]; // All topics share the same category name
		Long category_Id = (Long) results.get(0)[1]; // All topics share the same category ID

		List<String> topics = results.stream().map(row -> (String) row[0]).collect(Collectors.toList());

		return new CategoryWithTopicsDto(categoryUuid, categoryName, topics);
	}

	public TopicsDTO convertsToDTO(Topics topics) {
		return new TopicsDTO(topics.getUuid(), topics.getName(), topics.getCreatedBy());
	}

	public List<TopicsDTO> getAllTopics() {
		return topicsRepository.findAll().stream().map(this::convertsToDTO).collect(Collectors.toList());
	}

	public Optional<String> updateTopics(String topicsUuid, Topics topics) {
		Optional<Topics> existingTopic = topicsRepository.findByUuid(topicsUuid);

		if (existingTopic.isPresent()) {
			Topics existingTopicname = existingTopic.get();
			existingTopicname.setName(topics.getName());
			topicsRepository.save(existingTopicname);
			return Optional.of(existingTopicname.getName());
		} else {
			throw new ResourceNotFoundException("Topic not found with UUID " + topicsUuid);
		}
	}

	public DescriptionDTO convertsDescriptionToDTO(String description) {
		return new DescriptionDTO(description);
	}

	public Optional<DescriptionDTO> findAndMarkDescriptionAsReadByTopicsUuid(String topicsUuid, String token) {
		Optional<DescriptionDTO> description = topicsRepository.findDescriptionByTopicsUuid(topicsUuid).stream()
				.map(this::convertsDescriptionToDTO).findFirst();

		if (description.isPresent()) {

			String username = jwtservice.extractUserName(token);
			Users users = userRepository.findByUserName(username)
					.orElseThrow(() -> new UsernameNotFoundException("user name not "));
			Topics topicsid = topicsRepository.findByUuid(topicsUuid)
					.orElseThrow(() -> new TopicNotFoundException("Topic not found"));

			var role = users.getRole();
			if (role.equals(Role.ATTENDEE)) {
				TopicsReadStatus readstatus = new TopicsReadStatus();
				readstatus.setTopics(topicsid);
				readstatus.setUsers(users);
				readstatus.setUser_uuid(users.getUuid());
				readstatus.setUserName(users.getUserName());
				readstatus.setRole(users.getRole());
				readstatus.setReadAt(LocalDateTime.now());

				topicReadStatusRepository.save(readstatus);
			}

			return description;
		}
		return Optional.empty();

	}

}
