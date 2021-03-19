package com.lb.books.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lb.books.model.BookBlurb;
import com.lb.books.model.User;

@Repository
public interface BookBlurbRepository extends CrudRepository<BookBlurb, Long> {

//	find all, and orders all by when they were created. Earliest comes first because of descending. 
	List<BookBlurb> findAllByOrderByCreatedAtDesc();

//	find all by a specific user, and orders all by when they were created. Earliest comes first. 
	List<BookBlurb> findAllByUserOrderByCreatedAtDesc(User user);

	
	List<BookBlurb> findAllByUserInOrderByCreatedAtDesc(List<User> users);

//	all classes can use this. find blurbs by booktags earliest are first. 
	public List<BookBlurb> findByBookTags_PhraseOrderByCreatedAtDesc(String phrase);
	
	
}