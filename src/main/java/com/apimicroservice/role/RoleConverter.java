package com.apimicroservice.role;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

	/* Converter Models to DTO */
	public RoleDTO entityToDto(Role role) {
		RoleDTO dto = new RoleDTO();
		dto.setId(role.getId());
		dto.setName(role.getName());
		dto.setDescription(role.getDescription());		
//		dto.setUsers(role.getUsers());
//		dto.setPermissions(role.getPermissions());
		return dto;
	}
	
	public List<RoleDTO> entityToDto(List<Role> role ){
		return	role.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
	}
	
	/* Converter DTO to Models */
	public Role dtoToEntity(RoleDTO dto) {
		Role role = new Role();
		role.setId(dto.getId());
		role.setName(dto.getName());
		role.setDescription(dto.getDescription());		
//		role.setUsers(dto.getUsers());
//		role.setPermissions(dto.getPermissions());
		return role;
	}
	
	public List<Role> dtoToEntity(List<RoleDTO> dto)
	{
		return dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
	}
}
