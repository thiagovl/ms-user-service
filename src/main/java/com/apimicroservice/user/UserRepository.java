package com.apimicroservice.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	/* Query by Email */
	User findByEmail(String username);

	/* Query All Users */
	@Query(value = "SELECT * FROM tb_user", 
			nativeQuery = true)
	Page<User> findAllUserCustom(Pageable pageable);

	/* Query User by Name */
	/* Active unaccent CREATE EXTENSION unaccent; Postgres */
	@Query(value = "SELECT * FROM tb_user WHERE unaccent(name) ilike unaccent('%' || ?1 || '%')", 
			nativeQuery = true)
	Page<User> findByNameCustom(String name, Pageable pageable);
	
	/* Query by Id */
	@Query("select u from User u where u.id = ?1")
	User findByIdUser(Long id);
	
	

}