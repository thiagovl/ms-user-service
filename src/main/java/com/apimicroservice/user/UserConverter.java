package com.apimicroservice.user;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/* Converter Models to DTO */
	public UserDTO entityToDto(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setUserStatus(user.getUserStatus());
		dto.setRole(user.getRole());
//		dto.setAuthorizations(user.getAuthorizations());
		
		
		return dto;
	}
			
	public List<UserDTO> entityToDto(List<User> user ){
		return	user.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
	}
	
	public List<UserDTO> entityToDtoPage(List<User> user ){
		return	user.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
	}
	
	/* Converter DTO to Models */
	public User dtoToEntity(UserDTO dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setUserStatus(dto.getUserStatus());
		user.setRole(dto.getRole());
//		user.setAuthorizations(dto.getAuthorizations());
		return user;
	}
	
	public List<User> dtoToEntity(List<UserDTO> dto)
	{
		return dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
	}
}
