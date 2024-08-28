package com.demo.token.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.token.dto.UsersDTO;
import com.demo.token.model.Users;
import com.demo.token.model.Users.Role;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users>findByUserName(String userName);
	Optional<Users>findByuserName(String userName);
	Optional<Users> findByRole(Role role);
	List<Users> findAllByRole(Role role);

	Optional<Users> findByuserNameAndIsActive(String userName, boolean b);
	Optional<Users> findByUuid(String uuid);

	
	// Find all users where isActive is true
    List<Users> findByIsActiveTrue();

    // Find all users where isActive is false
    List<Users> findByIsActiveFalse();
}
