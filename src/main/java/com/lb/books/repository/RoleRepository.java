package com.lb.books.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lb.books.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	 Role findByRole(String role);
}