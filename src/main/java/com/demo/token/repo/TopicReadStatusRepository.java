package com.demo.token.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.token.model.TopicsReadStatus;

public interface TopicReadStatusRepository extends JpaRepository<TopicsReadStatus, Long>{

}
