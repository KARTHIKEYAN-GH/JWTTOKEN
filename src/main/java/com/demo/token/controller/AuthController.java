package com.demo.token.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.token.dto.UsersDTO;
import com.demo.token.model.AuthenticationResponse;
import com.demo.token.model.Category;
import com.demo.token.model.ResourceNotFoundException;
import com.demo.token.model.Users;
import com.demo.token.repo.UserRepository;
import com.demo.token.serviceImpl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class AuthController {
	private final UserRepository userRepository;
	private final UserServiceImpl userService;

	public AuthController(UserServiceImpl usersService, UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
		this.userService = usersService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody Users users) {
		try {
			UsersDTO savedUserDTO = userService.createUser(users);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users request) {
		try {
			AuthenticationResponse response = userService.authenticate(request);
			return ResponseEntity.ok(response);
		} catch (UsernameNotFoundException e) {
			// Handle case where the userName does not exist
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
		} catch (BadCredentialsException e) {
			// Handle case where the credentials are invalid
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
		} catch (IllegalStateException e) {
			// Handle case where the user is inactive
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		} catch (Exception e) {
			// Handle any other unexpected errors
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during authentication.");
		}
	}

	@PutMapping("update/{uuid}")
	public ResponseEntity<?> updateUser(@PathVariable("uuid") String uuid, @RequestBody Users request) {
		try {
			userService.updateUser(uuid, request);
			return ResponseEntity.ok("User updated successfully");
		} catch (IllegalArgumentException  e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
	}

	@DeleteMapping("/delete/{Uuid}")
	public ResponseEntity<String> deactiveUser(@PathVariable String Uuid) {
		try {
		Optional<String> userName = userService.deactiveUser(Uuid);
		if (userName.isPresent()) {
			return ResponseEntity.ok("User " + userName.get() + " Deletted Sucessfully ");
		} else {
			return ResponseEntity.ok("User not found or Faild to deltte");
		}
		}
	  catch (IllegalStateException e) {
	        // Handle specific exception when trying to deactivate an admin
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	    } catch (Exception e) {
	        // Handle other unexpected exceptions
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	    }
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() {
		List<UsersDTO> allUsers = userService.getAllUsers();
		if (allUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Users found");
		}
		return ResponseEntity.ok(allUsers);
	}

	@GetMapping("/getAllActiveUsers")
	public ResponseEntity<?> getAllActiveUsers() {
		List<UsersDTO> activeUsers = userService.getAllActiveUsers();
		if (activeUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Active Users found");
		}
		return ResponseEntity.ok(activeUsers);

	}

	@GetMapping("/getAllInActiveUsers")
	public ResponseEntity<?> getAllInactiveUsers() {
		List<UsersDTO> inactiveUsers = userService.getAllInactiveUsers();
		if (inactiveUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No In-Active Users found");
		}
		return ResponseEntity.ok(inactiveUsers);
	}

	@GetMapping("/getAllTrainees")
	public ResponseEntity<?> getAllTrainees() {
		List<UsersDTO> traineeUsers = userService.getAllTrainees();
		if (traineeUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Trainee Found");
		}
		return ResponseEntity.ok(traineeUsers);
	}

	@GetMapping("/getAllAttendees")
	public ResponseEntity<?> getAllAttendees() {
		List<UsersDTO> AttendeeUsers = userService.getAllAttendees();
		if (AttendeeUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Attendee Found");
		}
		return ResponseEntity.ok(AttendeeUsers);
	}

	@GetMapping("/getUserBy/{Uuid}")
	public ResponseEntity<?> getUserByUuid(@PathVariable String Uuid) {
		Optional<Users> existingUser = userService.getUsersByUuid(Uuid);
		if (existingUser.isPresent()) {
			return ResponseEntity.ok(existingUser);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found ");
	}
}