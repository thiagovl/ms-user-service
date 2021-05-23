package com.apimicroservice.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RoleController {
	
	@Autowired
	RoleService service;
	
	@Autowired
	RoleConverter converter;
	
	/* List All Roles and Seach by Name */
	@GetMapping
	public ResponseEntity<?> getAllRole(
			@RequestParam(required = false) String name,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size){
							
			try {			
				
				Pageable paging = PageRequest.of(page, size);
		
				Page<RoleDTO> pageRoles;
				
				 if (name == null) {
					 pageRoles = service.findAllRoleCustom(paging);
				 }
				 else {
					 pageRoles = service.findByNameRoleCustom(name, paging);
				 }
				 
				 List<RoleDTO> roles = new ArrayList<RoleDTO>();				 				 
				 roles = pageRoles.getContent();
				 
				 Map<String, Object> response = new HashMap<>();
			     response.put("roles", roles);
			     response.put("currentPage", pageRoles.getNumber());
			     response.put("totalItems", pageRoles.getTotalElements());
			     response.put("totalPages", pageRoles.getTotalPages());
				 
				 if(!roles.isEmpty()) {
					 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				 }
				 return new ResponseEntity<String>("Não foi encontrada nenhuma Role!!!", HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("Ocorreu um erro no controller da requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}		
	}
	
	/* Get Role by Id */
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdRole(@PathVariable Long id){
		RoleDTO role = service.findByIdRole(id);
		try {
			if(role != null) {
				return new ResponseEntity<RoleDTO>(role, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Role com o Código '" + id + "' não encontrada!!!",  HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Ocorreu um erro no controller da requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/* Create Role */
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid RoleDTO role){
		RoleDTO entity = service.create(role);
		try {
			if(entity != null) {
				return new ResponseEntity<RoleDTO>(entity, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Não foi possível cadastrar a Role!!!",  HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Ocorreu um erro no controller da requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/* Update Role */
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid RoleDTO role){	
		try {
			role = service.update(id, role);
			if(role != null) {
				return new ResponseEntity<RoleDTO>(role, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Não foi possível atualizar a Role com o código " + id + "!!!",  HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Ocorreu um erro na requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/* Delete Role */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		String msg = service.delete(id);
		try {
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Ocorreu um erro no controller da requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/** Response BAD REQUEST */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}