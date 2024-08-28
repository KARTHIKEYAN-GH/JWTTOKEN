package com.demo.token.service;

import java.util.List;
import java.util.Optional;
import com.demo.token.dto.UsersDTO;
import com.demo.token.model.AuthenticationResponse;
import com.demo.token.model.Users;

public interface UsersService {
	 
	 UsersDTO createUser(Users users);
	
	 AuthenticationResponse authenticate(Users request);
	
	 Users updateUser(String Uuid, Users users); 
	
	 Optional<String> deactiveUser(String Uuid);
	
	 List<UsersDTO> getAllUsers();
	
	 List<UsersDTO> getAllActiveUsers();
	
	 List<UsersDTO> getAllInactiveUsers();
	
	public List<UsersDTO> getAllTrainees();
	
	public List<UsersDTO> getAllAttendees();
	
	Optional<Users>getUsersByUuid(String Uuid);
}
