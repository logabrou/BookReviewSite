package com.lb.books.model;


import java.util.Collection;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements UserDetails {

	
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
