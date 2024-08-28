package com.demo.token.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.token.dto.DescriptionDTO;
import com.demo.token.model.Category;
import com.demo.token.model.Topics;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long> {

	// @Query(nativeQuery = true, value = "SELECT t.name AS topic_name, c.id AS
	// category_id, c.name AS category_name FROM Topics t JOIN Category c ON
	// t.category_id = c.id WHERE t.category_uuid = :categoryUud")
	//	List<Object[]> findTopicAndCategoryNameByCategoryUuid(String categoryUuid);

	// List<Object[]> findTopicAndCategoryNamesByCategoryId(@Param("categoryId")
	// Long categoryId);
	// Optional<String> findDescriptionByTopicsId(@Param("id") Long id);

	// Optional<Topics> findByIdAndCategoryId(String topicsUuid, String
	// categoryUuid);

	@Query("SELECT t.name AS topic_name, c.id AS category_id, c.name AS category_name " + "FROM Topics t "
			+ "JOIN Category c ON t.category.id = c.id " + "WHERE c.uuid = :categoryUuid")
	List<Object[]> findTopicAndCategoryNameByCategoryUuid(@Param("categoryUuid") String categoryUuid);

	Optional<Topics> findByIdAndCategoryId(Long id, Long categoryId);

	@Query("SELECT t FROM Topics t WHERE t.id = :id AND t.category.uuid = :categoryUuid")
	Optional<Topics> findByIdAndCategoryUuid(@Param("id") String topicsUuid,
			@Param("categoryUuid") String categoryUuid);

	@Query(nativeQuery = true, value = "SELECT t.description FROM topics t WHERE t.uuid = :topicsUuid")
	Optional<String> findDescriptionByTopicsUuid(@Param("topicsUuid") String topicsUuid);

	// @Query("SELECT new com.example.dto.DescriptionDTO(t.description) FROM Topics
	// t WHERE t.uuid = :topicsUuid")
	// Optional<DescriptionDTO> findDescriptionByTopicsUuid(@Param("topicsUuid")
	// String topicsUuid);

	Optional<Topics> findByUuid(String topicsUuid);

}
