package com.lb.books.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lb.books.model.Book;
import com.lb.books.model.BookBlurb;
import com.lb.books.model.User;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

//	find all books and order all by the descending order of titles. 
	List<Book> findAllByOrderByTitleDesc();

//	find all books for a specific user and order by the title.
	List<Book> findAllByUserOrderByTitleDesc(User user);

	
	List<Book> findAllByUserInOrderByTitleDesc(List<User> users);

//	all classes can use this. find blurbs by booktags earliest are first. 
	public List<Book> findByBookTags_Phrase(String phrase);
	
}
