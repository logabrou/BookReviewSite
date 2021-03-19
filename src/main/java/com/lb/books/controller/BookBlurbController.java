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
import com.lb.books.model.BookBlurbDisplay;
import com.lb.books.repository.BookBlurbRepository;
import com.lb.books.repository.BookRepository;
import com.lb.books.repository.BookTagRepository;
import com.lb.books.service.*;
import com.lb.books.model.BookTag;
import com.lb.books.model.User;


@Controller
public class BookBlurbController {

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
	
	@Autowired
	private BookTagRepository bookTagRepo;
	
//	The idea is that the book that have been tagged are tagged within the blurbs or blog posts
	 
	  @GetMapping(value = "/blurbs")
	    public String getblurb(Model model) {
		    List<BookBlurb> blurb = (List<BookBlurb>)bookBlurbRepo.findAll();
	        model.addAttribute("blurb", blurb);
	        return "blogfeed";
	    }

	  
	  @PostMapping(value = "/blurbs")
	    public String submitBlurbForm(@Valid BookBlurb blurb, BindingResult bindingResult, Model model) {
	    	User user = userService.getLoggedInUser();
	        if (!bindingResult.hasErrors()) {
	        	blurb.setUser(user);
	            bookBlurbRepo.save(blurb);
	            model.addAttribute("successMessage", "Blurb successfully created!");
	            model.addAttribute("newBlurb", new BookBlurb());
	        }
	        return "newBlurb";
	    }
	  
	  
	   @GetMapping(value = "/blurbs/new")
	    public String getBlurbForm(Model model) {
	        model.addAttribute("blurb", new BookBlurb());
	        return "newBlurb";
	    }
	   
//	   I'm not sure if bookTags is an html page yet?
	   @GetMapping(value = "/bookTags")
	    public String getBookTags(Model model) {
	    	List<BookTag> tag = (List<BookTag>)bookTagRepo.findAll();
	    	model.addAttribute("tagList", tag);
	    	return "bookTags";
	    }
	   
	   @GetMapping(value = "/bookTags/{tag}")
	    public String getBlurbsByTag(@PathVariable(value="bookTag") String bookTag, Model model) {
	    	List<BookBlurbDisplay> blurbs = bookBlurbService.findAllWithTag(bookTag);
	    	model.addAttribute("blurbList", blurbs);
	    	model.addAttribute("bookTag", bookTag);
	    	return "taggedBooks";
	    }
	
	
	
}