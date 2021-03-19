package com.lb.books.model;


import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "user_id")
	  private Long id;
	  
	  @Email(message = "Please provide a valid email")
	  @NotEmpty(message = "Please provide an email")
	  private String email;
	  
	  private String userName;
	  
	  private String passWord;
	   
	  
	  @NotEmpty(message = "Please provide your first name")
	  private String firstName;
	  
	  @NotEmpty(message = "Please provide your last name")
	  private String lastName;
	  
	  private Integer active;
	  
	  @CreationTimestamp
	  private Date createdAt;
	  
	  @ManyToMany(cascade = CascadeType.ALL)
	  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
	  inverseJoinColumns = @JoinColumn(name = "role_id"))
	  private Set<Role> roles;
	  
	  @ManyToMany(cascade = CascadeType.ALL)
	  @JoinTable(name = "user_book", joinColumns = @JoinColumn(name = "user_id"), 
	  inverseJoinColumns = @JoinColumn(name = "book_id"))
	  private List<Book> books;
	
	
}
