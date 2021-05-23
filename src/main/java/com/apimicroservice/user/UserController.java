package com.apimicroservice.user;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apimicroservice.exceptions.ResourceNotFoundException;
import com.apimicroservice.security.AuthRequest;
import com.apimicroservice.security.JwtUtil;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	UserConverter converter;

	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwt;
	
	/* Register */
	@PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()) 
            );
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ocorreu um erro na requisição. Usuário/Senha invalidos!");
        }
        
        return "Bearer " + jwt.generateToken(authRequest.getEmail());
    }
	
	/* List All Users and Seach by Name */
	@GetMapping
	public ResponseEntity<?> getAllUser(
			@RequestParam(required = false) String name,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size){
							
			try {			
				
				Pageable paging = PageRequest.of(page, size);
		
				Page<UserDTO> pageUsers;
				
				 if (name == null) {
					 pageUsers = service.findAllUserCustom(paging);
				 }
				 else {
					 pageUsers = service.findByNameCustom(name, paging);
				 }
				 
				 List<UserDTO> users = new ArrayList<UserDTO>();				 				 
				 users = pageUsers.getContent();
				 
				 Map<String, Object> response = new HashMap<>();
			     response.put("users", users);
			     response.put("currentPage", pageUsers.getNumber());
			     response.put("totalItems", pageUsers.getTotalElements());
			     response.put("totalPages", pageUsers.getTotalPages());
				 
				 if(!users.isEmpty()) {
					 return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				 }
				 return new ResponseEntity<String>("Não foi encontrado nenhum usuário!!!", HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				return new ResponseEntity<String>("Ocorreu um erro no controller da requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}		
	}
	
	/* Get User by email */
	@PostMapping("/email")
	public ResponseEntity<?> findByIdUser(@RequestBody UserEmail email){
		UserDTO user = service.findByEmailUser(email.getEmail());
		try {
			if(user != null) {
				return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Usuario com o Código '" + email.getEmail() + "' não encontrado!!!",  HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Ocorreu um erro no controller da requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/* Get User by Id */
	@GetMapping("/{id}")
	public ResponseEntity<?> findByIdUser(@PathVariable Long id){
		UserDTO user = service.findByIdUser(id);
		try {
			if(user != null) {
				return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Usuario com o Código '" + id + "' não encontrado!!!",  HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Ocorreu um erro no controller da requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/* Create User */
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid UserDTO user){
		UserDTO entity = service.create(user);
		try {
			if(entity != null) {
				return new ResponseEntity<UserDTO>(entity, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Não foi possível cadastrar o usuário",  HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Ocorreu um erro no controller da requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/* Update User */
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UserDTO user){
		try {
			user = service.update(id, user);
			if(user != null) {
				return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Não foi possível atualizar a Role com o código '" + id + "'!!!",  HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Ocorreu um erro na requisição: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/* Delete User */	
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