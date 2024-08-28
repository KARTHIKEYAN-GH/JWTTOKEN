package com.demo.token.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.demo.token.dto.UsersDTO;
import com.demo.token.model.AuthenticationResponse;
import com.demo.token.model.ResourceNotFoundException;
import com.demo.token.model.Users;
import com.demo.token.model.Users.Role;
import com.demo.token.repo.UserRepository;
import com.demo.token.service.UsersService;
import com.demo.token.validation.PhoneNumberValidation;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UsersService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtService jwtService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	public UsersDTO convertToDTO(Users users) {

		return new UsersDTO(users.getUuid(), users.getName(), users.getEmail(), users.getUserName(), users.getRole(),
				users.getIsActive());

	}

	@Transactional
	public UsersDTO createUser(Users users) {
		// Validate the input
		if (users.getUserName() == null || users.getPassword() == null || users.getRole() == null || users.getEmail()==null) {
			throw new IllegalArgumentException("username, password, email and role must be provided.");
		}
		if (!PhoneNumberValidation.isValid(users.getPhoneNumber())) 
		{
            throw new IllegalArgumentException("Invalid phone number format");
        }

		// Check if the userName already exists and is active
		Optional<Users> existingUser = userRepository.findByuserNameAndIsActive(users.getUserName(), true);
		if (existingUser.isPresent()) {
			throw new IllegalStateException("A User with this UserName already exists and is Active.");
		} else {

			// Check if ADMIN already exists or not
			if (users.getRole().equals(Role.ADMIN)) {
				Optional<Users> existAdmin = userRepository.findByRole(Role.ADMIN);
				if (existAdmin.isPresent()) {
					throw new IllegalStateException(
							"Only one ADMIN is allowed. please check the role .The existing ADMIN can create users with roles TRAINEE or ATTENDEE.");
				}
			}

			// Set the user to active and encode the password
			users.setActive(true);
			users.setPassword(passwordEncoder.encode(users.getPassword()));

			// Save the new user
			Users savedUsers = userRepository.save(users);
			return convertToDTO(savedUsers);
		}
	}

	public AuthenticationResponse authenticate(Users request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		Users users = userRepository.findByuserName(request.getUserName()).orElseThrow();
		if (users.isActive()) {
			String token = jwtService.generateToken(users);
			return new AuthenticationResponse(token);
		} else {
			// Handle the case where the user is not active
			throw new IllegalStateException("User is inactive. Please contact support.");
		}
	}

	@Transactional
	public Users updateUser(String uuid, Users users) {
		if (!PhoneNumberValidation.isValid(users.getPhoneNumber())) 
		{
            throw new IllegalArgumentException("Invalid phone number format");
        }
		Users user = userRepository.findByUuid(uuid)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + uuid));
		user.setName(users.getName());
		user.setEmail(users.getEmail());
		user.setPhoneNumber(users.getPhoneNumber());
		user.setUserName(users.getUserName());
		user.setPassword(users.getPassword());
		user.setRole(users.getRole());
		user.setActive(true);
		return userRepository.save(user);
	}

	public Optional<String> deactiveUser(String Uuid) {
		Users deletedUser = userRepository.findByUuid(Uuid).orElseThrow(() -> new RuntimeException("user not found"));
		if(deletedUser.getRole().equals(Role.ADMIN))
		{
			throw new IllegalStateException("Yor are trying to delete a Admin !!!!.");
		}
	
		deletedUser.setActive(false);
		userRepository.save(deletedUser);
		return Optional.of(deletedUser.getName());
	}

	public List<UsersDTO> getAllUsers() {
		return userRepository.findAll().stream()// Convert list to Stream
				.map(this::convertToDTO) 		 // Convert each Users to UsersDTO
				.collect(Collectors.toList());// Collect results into a List<UsersDTO>
	}

	public List<UsersDTO> getAllActiveUsers() {
		return userRepository.findByIsActiveTrue().stream().map(this::convertToDTO) 
				.collect(Collectors.toList());
	}

	public List<UsersDTO> getAllInactiveUsers() {
		return userRepository.findByIsActiveFalse().stream().map(this::convertToDTO) // Convert each User to UserDTO
				.collect(Collectors.toList());
	}

	public List<UsersDTO> getAllTrainees() {
		return userRepository.findAllByRole(Role.TRAINEE).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public List<UsersDTO> getAllAttendees() {
		return userRepository.findAllByRole(Role.ATTENDEE).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	public Optional<Users> getUsersByUuid(String Uuid) {

		return userRepository.findByUuid(Uuid);
	}
}
