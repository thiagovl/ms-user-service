package com.apimicroservice.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	/* Query All Roles */
	@Query(value = "SELECT * FROM tb_role", 
			nativeQuery = true)
	Page<Role> findAllRoleCustom(Pageable pageable);

	/* Query Role by Name */
	@Query(value = "SELECT * FROM tb_role WHERE unaccent(name) ilike unaccent('%' || ?1 || '%')", 
			nativeQuery = true)
	Page<Role> findByNameRoleCustom(String name, Pageable pageable);
	
	/* Query by Id */
	@Query("select u from Role u where u.id = ?1")
	Role findByIdRole(Long id);
}