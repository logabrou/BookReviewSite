package com.lb.books.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lb.books.model.BookBlurb;
import com.lb.books.model.BookBlurbDisplay;
import com.lb.books.model.User;
import com.lb.books.repository.BookBlurbRepository;
import com.lb.books.repository.BookRepository;
import com.lb.books.repository.BookTagRepository;



@Service
public class BookBlurbService {

    
//    Bookblurb connects to Book class, but is only displayed on user page. Click on a book,
//    and the bookblurb is displayed. Booktags are displayed on both. Bookblurb service should handle the way 
//    bookblurb is structured by bookblurbdisplay. It needs to structure blurbs about a particular book into lists.
    
    
    
    @Autowired
    private BookBlurbRepository bookBlurbRepo;
	
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private BookTagRepository bookTagRepo;
    
    
//    find all book blurbs in the database.Takes each bookblurb and associate it with bookblurb display
//    (which is a class that has a user, a book, a blurb, and a list of booktags) and returns a list of 
//    those displays for each bookblurb in the list. 
    public List<BookBlurbDisplay> findAll() {
        List<BookBlurb> blurbs = bookBlurbRepo.findAllByOrderByCreatedAtDesc();
        return formatTimestamps(blurbs);
    }

//    find all the books that are associated with only a single user
    public List<BookBlurbDisplay> findAllByUser(User user) {
    	List<BookBlurb> blurbs = bookBlurbRepo.findAllByUserOrderByCreatedAtDesc(user);
        return formatTimestamps(blurbs);
    }

//    find all book blurbs written by the given list of users
    public List<BookBlurbDisplay> findAllByUsers(List<User> users) {
    	List<BookBlurb> blurbs = bookBlurbRepo.findAllByUserInOrderByCreatedAtDesc(users);
        return formatTimestamps(blurbs);
    }

    public List<BookBlurbDisplay> findAllWithTag(String bookTag) {
    	List<BookBlurb> blurbs = bookBlurbRepo.findByBookTags_PhraseOrderByCreatedAtDesc(bookTag);
        return formatTimestamps(blurbs);
    }
    
    
//    Take in a list of book blurbs corresponding to all blurbs, a single user's blurbs, or a list of users blurbs.
//    Each bookBlurb object will become a bookblurbDisplay object. Set date and time. Create new Date object.
//    Loop through blurbs, and for each bookBlurb create a bookBlurbDisplay. 
    private List<BookBlurbDisplay> formatTimestamps(List<BookBlurb> bookBlurbs) {
    	List<BookBlurbDisplay> response = new ArrayList<>();
        PrettyTime prettyTime = new PrettyTime();
        SimpleDateFormat simpleDate = new SimpleDateFormat("M/d/yy");
        Date now = new Date();
        for (BookBlurb blurb : bookBlurbs) {
        	BookBlurbDisplay bookBlurbDisplay = new BookBlurbDisplay();
        	bookBlurbDisplay.setUser(blurb.getUser());
        	bookBlurbDisplay.setMessage(blurb.getMessage());
        	bookBlurbDisplay.setBookTags(blurb.getBookTags());
            long diffInMillies = Math.abs(now.getTime() - blurb.getCreatedAt().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diff > 3) {
            	bookBlurbDisplay.setDate(simpleDate.format(blurb.getCreatedAt()));
            } else {
            	bookBlurbDisplay.setDate(prettyTime.format(blurb.getCreatedAt()));
            }
            response.add(bookBlurbDisplay);
        }
        return response;
    }
    

}
