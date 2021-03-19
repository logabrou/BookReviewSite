package com.lb.books.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lb.books.model.Book;
import com.lb.books.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUserName(String userName);
}