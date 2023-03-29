package com.shoppingwithme.hoangkimshop.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDeatilsServices();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService());
		return authProvider;
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* http.csrf().disable(); */
		 http.authorizeRequests()
         .antMatchers("/js/**", "/css/**","/images/**","/vendor/**","/img/**","/fonts/**","/sass/**","/assets/**").permitAll()
         .antMatchers("/").permitAll()
         .antMatchers("/register").permitAll()
         .antMatchers("/shopping-with-me/users/exists-username").permitAll()
         .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
         .antMatchers("/shopping-with-me/img/**").permitAll()
         .antMatchers("/shopping-with-me/js/**").permitAll()
         .antMatchers("/shopping-with-me/cart/**").hasAnyAuthority("USER")
         .and()
     .formLogin()
         .loginPage("/login")
         .usernameParameter("username")
         .successHandler(new AuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				redirectStrategy.sendRedirect(request, response, "/");
				
			}
		})
         .permitAll()
         .and()
         .logout()
         .permitAll()
         .and()
         .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
         .and();
         
		
		
		
	}

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
//         .anyRequest().authenticated()
	
}
