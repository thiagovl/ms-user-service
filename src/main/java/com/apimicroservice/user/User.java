package com.apimicroservice.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.apimicroservice.role.Role;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserStatus userStatus;      
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

	public User() {
		super();
	}

	public User(Long id, String name, String email, String password, UserStatus userStatus, Role role) {
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