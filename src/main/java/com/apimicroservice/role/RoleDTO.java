package com.apimicroservice.role;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class RoleDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "O campo NOME é obrigatório!")
	private String name;
	
	@NotBlank(message = "O campo DESCRIÇÃO é obrigatório!")
	private String description;

	public RoleDTO() {
		super();
	}

	public RoleDTO(Long id, @NotBlank(message = "O campo NOME é obrigatório!") String name,
			@NotBlank(message = "O campo DESCRIÇÃO é obrigatório!") String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}