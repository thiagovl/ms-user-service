package com.apimicroservice.user;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.apimicroservice.role.Role;

public class UserDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "O NOME é obrigatório!")
    private String name;
	
	@NotBlank(message = "O EMAIL é obrigatório!")
    private String email;
	
	@NotBlank(message = "A SENHA é obrigatória!")
    private String password;
	
    private UserStatus userStatus;     
	
	private Role role;

	public UserDTO() {
		super();
	}

	public UserDTO(Long id, @NotBlank(message = "O NOME é obrigatório!") String name,
			@NotBlank(message = "O EMAIL é obrigatório!") String email,
			@NotBlank(message = "A SENHA é obrigatória!") String password, UserStatus userStatus, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.userStatus = userStatus;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}