package com.lb.books.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.lb.books.service.BookBlurbService;
import com.lb.books.service.BookService;
import com.lb.books.service.UserService;
import com.lb.books.model.BookBlurbDisplay;
import com.lb.books.model.BookDisplay;
import com.lb.books.model.User;
import com.lb.books.model.Book;
import com.lb.books.model.BookBlurb;

@Controller
public class UserController {

	  @Autowired
	  private UserService userService;
	  @Autowired
	  private BookService bookService;
	  @Autowired
	  private BookBlurbService bookBlurbService;
	
	
	
//	I need USERS to be able "follow" books. Books will play the part of followers for now. 
//	  Maybe this could return all the users, but instead of their user followers I can display
//	  books in similar with the logged in user. 
//	  This url mapping displays a non logged in account that shows some 
//	  of the particular user books. 
	  @GetMapping(value = "/users/{username}")
	  public String getUser(@PathVariable(value="username") String username, Model model) {
	    User user = userService.findByUsername(username);
	    List<BookBlurbDisplay> usersLikedBooks = bookBlurbService.findAllByUser(user);
	    User loggedInUser = userService.getLoggedInUser();
	    List<Book> loggedInBooks = loggedInUser.getBooks();
	    List<Book> userBooks = user.getBooks();
	    HashSet<Book> userHash = new HashSet<>();
	    for (Book item : loggedInBooks) {
	    	userHash.add(item);
	    }
	    List<Book> isSimilar = null;
	    List<Book> isDifferent = null;
	    for (Book book : userBooks) {
	      if (userHash.contains(book)) {
	        isSimilar.add(book);
	      } else {
	    	  isDifferent.add(book);
	      }
	    }
//	    I need to determine the difference in bookBlurbDisplay and how it functions
	    boolean isSelfPage = loggedInUser.getUsername().equals(username);
	    model.addAttribute("isSelfPage", isSelfPage);
	    model.addAttribute("isSimilar", isSimilar);
	    model.addAttribute("isDifferent", isDifferent);
	    model.addAttribute("user", user);
	    return "user";
	  }
	  
	  
//	  Maybe this could return all the users, but instead of their user followers I can display
//	  books in similar with the logged in user. So after /users there is some "?key=value" pair incoming
//	  with the key named filter, while filter equals the value associated with the key. It's also optional, 
//	  so if there is no filter key then the value is null.
	  @GetMapping(value = "/users")
		public String getBooks(@RequestParam(value = "filter", required = false) String filter, Model model) {
			List<User> users = new ArrayList<User>();

			User loggedInUser = userService.getLoggedInUser();

			List<Book> usersFollowing = loggedInUser.getBooks();
//			List<User> usersFollowers = loggedInUser.getFollowers();
			if (filter == null) {
				filter = "all";
			}

			else {
				users = userService.findAll();
				model.addAttribute("filter", "all");
			}
			model.addAttribute("users", users);

			setBookCounts(users, model);
			setBookBlurbCounts(users, model);
//			setLikedStatus(book, model);

			return "users";
		}
//	  
	  
	  
//	  Count in a hashmap the number of book blurbs each user has created. 
	  private void setBookBlurbCounts(List<User> users, Model model) {
	    HashMap<String, Integer> bookBlurbCounts = new HashMap<>();
	    for (User user : users) {
	      List<BookBlurbDisplay> bookBlurbs = bookBlurbService.findAllByUser(user);
	      bookBlurbCounts.put(user.getUsername(), bookBlurbs.size());
	    }
	    model.addAttribute("bookBlurbCounts", bookBlurbCounts);
	  }
	  

	  
	  private void setBookCounts(List<User> users, Model model) {
	    HashMap<String, Integer> bookCounts = new HashMap<>();
	    for (User user : users) {
	      List<Book> books = bookService.findAllByUser(user);
	      bookCounts.put(user.getUsername(), books.size());
	    }
	    model.addAttribute("bookCounts", bookCounts);
	  }
	  

	  
//	  User sets a book to liked and it displays on their page. 
	  private void setLikedStatus(Book book, Model model) {
		    HashMap<String, Boolean> likedStatus = new HashMap<>();
		    User user = userService.getLoggedInUser();
		    List<Book> books = user.getBooks();
		      if (!books.contains(book)) {
		    	  likedStatus.put(book.getTitle(), true);
		      } 
		    
		    model.addAttribute("likedStatus", likedStatus);
		  }
	  
	  
	  
	  
//	  
	  
}
