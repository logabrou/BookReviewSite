package com.lb.books.model;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;

import com.lb.books.repository.BookBlurbRepository;
import com.lb.books.repository.BookRepository;
import com.lb.books.repository.BookTagRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookBlurbDisplay {
	
//	This class structures a book, a user, blog posts, and booktags.

	
    private User user;
    private Book book;
    private String message;
    private String date;
    private List<BookTag> bookTags;
    
    
}
