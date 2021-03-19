package com.lb.books.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.lb.books.model.Book;
import com.lb.books.model.BookBlurb;
import com.lb.books.model.BookBlurbDisplay;
import com.lb.books.model.User;
import com.lb.books.repository.BookRepository;


@Service
public class BookService {

	
//	Google books API
	@Value("${api_key}")
	private String apiKey;
	
	@Autowired
	private BookRepository bookRepo;
	
//	The list of all possible books in the book repository
	  public List<Book> findAll() {
	        List<Book> books = bookRepo.findAllByOrderByTitleDesc();
	        return books;
	    }

//	    find all the books that are associated with only a single user
	    public List<Book> findAllByUser(User user) {
	    	List<Book> books = bookRepo.findAllByUserOrderByTitleDesc(user);
	        return books;
	    }

//	    find all book lists for the given list of users
	    public List<Book> findAllByUsers(List<User> users) {
	    	List<Book> books = bookRepo.findAllByUserInOrderByTitleDesc(users);
	        return books;
	    }

	    public List<Book> findAllWithTag(String bookTag) {
	    	List<Book> books = bookRepo.findByBookTags_Phrase(bookTag);
	        return books;
	    }
	
	
//	    The book class needs to be based on the values extracted from the api call. 
	    public Book getBookInfo(String title) {
	    	String URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:${bookTitle}&key=${apiKey}";
	    	RestTemplate restTemplate = new RestTemplate();      
	        try {   
		    		Book toSaveBook = new Book();
		    		toSaveBook.setTitle(title);
		    		bookRepo.save(toSaveBook);        	
		            return restTemplate.getForObject(URL, Book.class);
	        }
	        catch (HttpClientErrorException ex) {
	            Book book = new Book();
	            book.setTitle("error");
	            return book;
	        }
	    }
	    
	    
	
}
