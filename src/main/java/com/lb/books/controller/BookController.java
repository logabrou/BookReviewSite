package com.lb.books.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lb.books.model.Book;
import com.lb.books.model.BookBlurb;
import com.lb.books.repository.BookBlurbRepository;
import com.lb.books.repository.BookRepository;
import com.lb.books.service.*;
import com.lb.books.model.User;


@Controller
public class BookController {

	@Autowired
    private UserService userService;
	
	@Autowired
	private BookBlurbService bookBlurbService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private BookBlurbRepository bookBlurbRepo;
	
	
//	This page will be for the logged in user homepage. I will do book list here. On another page 
//	I will do the book blurb list. 
	 @GetMapping(value= {"/"})
	    public String getFeed(@RequestParam(value="filter", required=false) String filter, Model model){
	    	User loggedInUser = userService.getLoggedInUser();
	    	List<Book> books = new ArrayList<>();
	    	books = bookRepo.findAllByUserOrderByTitleDesc(loggedInUser);
	        model.addAttribute("bookList", books);
	        return "feed";
	    }
	 
	 
//	 Do I need validation here?
//	 There needs to be way to click on a new book and add that to the user's feed.
	  @PostMapping(value = "/{title}/add")
	    public String addBook(@Valid Book book, BindingResult bindingResult, Model model) {
	    	User loggedInUser = userService.getLoggedInUser();
	        if (!bindingResult.hasErrors()) {
	        	book.setUser(loggedInUser);
	        	bookRepo.save(book);
	        	List<Book> books = new ArrayList<>();
	        	books = bookRepo.findAllByUserOrderByTitleDesc(loggedInUser);
		        model.addAttribute("bookList", books);
	        }
	        return "feed";
	    }
	  

		@PostMapping(value = "/{title}/delete")
		public String unfollow(@PathVariable(value = "title") Book book, HttpServletRequest request) {
			User loggedInUser = userService.getLoggedInUser();
			List<Book> books = new ArrayList<>();
			books.remove(book);
			loggedInUser.setBooks(books);
			userService.save(loggedInUser);
			return "redirect:" + request.getHeader("Referer");
		}
	
}
