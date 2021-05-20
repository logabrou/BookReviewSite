package com.lb.books.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    private DataSource dataSource;
    
//    @Value("${spring.queries.users-query}")
//    private String usersQuery;
//
//    @Value("${spring.queries.roles-query}")
//    private String rolesQuery;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.
//             jdbcAuthentication()           
//           .usersByUsernameQuery(usersQuery)
//           .authoritiesByUsernameQuery(rolesQuery)
//               .dataSource(dataSource)
//               .passwordEncoder(bCryptPasswordEncoder);
//    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
               userDetailsService(userDetailsService)
               .passwordEncoder(bCryptPasswordEncoder);
    }
    
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.       	
                authorizeRequests()
                .antMatchers("/login", "/signup", "/console/**", "/custom.js","/custom.css").permitAll()
//                .antMatchers().hasAuthority("USER").anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
//                .loginProcessingUrl("/signup")
                .failureUrl("/login?error=true");
//                .defaultSuccessUrl("/user")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .and().logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login").and().exceptionHandling();
        
        http.headers().frameOptions().disable();
    }
    
//    @Override
//    @Bean
////  Retrieves users from the database
//    protected UserDetailsService userDetailsService() {
////    	UserDetails user = User.builder()
////    				.username(null)
////    				.password(null)
////    				.roles("USER")
////    				.build();
//    	User user = user.getLoggedInUser();
//    	String username = User user.ge
//    	
//    	User.builder()
//    				.username(user.getLoggedInUser().getUserName())
//    				.password(bCryptPasswordEncoder.encode(user.getPassword()))
//    				.roles(null)
//    				.build();
//    	
//    	return new InMemoryUserDetailsManager(
//    			user
//    	);
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//    }
}