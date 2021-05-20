package com.lb.books.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "book_id")
	  private Long id;
	  
	 
	  @ElementCollection
	  private Map<String, String> volumeInfo;
	  
	  private String genre;
	  
	  private String description;
	  
	  private Integer pageCount;
	  
	  private String mainCategory;
	  
	 
	  @ElementCollection
	  private List<String> categories;
	  
	  private String review;
	  
	  private Double averageRating;
	  
	  private Integer ratingsCount;
	  	  
	  private String title;
	  
	  private String author;
	  
	
	  @ElementCollection
	  private Map<String, String> imageLinks;
	  
      @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	  @JoinTable(name = "book_bookTag", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "bookTag_id"))
      private List<BookTag> bookTags;
	  
	  @ManyToOne(fetch = FetchType.LAZY, optional = false)
	  @JoinColumn(name = "user_id")
	  private User user;
	  

}
