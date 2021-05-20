package com.lb.books.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lb.books.repository.BookRepository;
import com.lb.books.repository.UserRepository;
import com.lb.books.model.Role;
import com.lb.books.model.User;
import com.lb.books.repository.RoleRepository;

@Service
public class UserService implements UserDetailsService{
	
	private UserRepository userRepo;
	private BookRepository bookRepo;
	private RoleRepository roleRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
    public UserService(UserRepository userRepository, 
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepository;
        this.roleRepo = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
//	Method to save a new user. If user is in fact new, encode password, set password as encoded password.
//	If user is logged in, set their status as active. Each user can have only one of each role, so use a 
//	set for each role. Here, we have a hash set. I guess we are hashing each user's unique role with a hash
//	function. If active and a user, set active role as user? 
    public User saveNewUser(User user) {
        user.setPassWord(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepo.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepo.save(user);
    
   }
	
//    Returns/Retrieves the User object with given username. SecurityContextHolder can access the security context object of SecutriyContext. GetAuthentication
//    returns the principal, then we can get their name. Then return the User object associated with the
//    userName.
    public User getLoggedInUser() {
    	return findByUsername(SecurityContextHolder.
                getContext().getAuthentication().getName());
    }
    

    
	
//	These are CRUD operations, methods to interact with database. 
	public User findByUsername(String username) {
        return userRepo.findByUserName(username);
    }
	
//	Method to return all users in a list from database
	public List<User> findAll(){
        return (List<User>) userRepo.findAll();
    }
	
    public void save(User user) {
        userRepo.save(user);
    }

//   UserDetailsService allows for this method to be used to retrieve userdetails. 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User user = findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException ("Username not found");
			}
			return user;

	}
    
	

	
	
	
	

}
