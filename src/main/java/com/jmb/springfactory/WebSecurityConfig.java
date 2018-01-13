package com.jmb.springfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationEntryPoint restAuthenticationEntryPoint;

  @Autowired
  private AuthenticationFailureHandler restAuthenticationFailureHandler;

  @Autowired
  private AuthenticationSuccessHandler restAuthenticationSuccessHandler;
  
  @Autowired
  private AuthenticationProvider customAuthenticationProvider;
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(customAuthenticationProvider);
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
      .csrf().disable()

    .authorizeRequests()    
        .antMatchers("/login").permitAll()
        .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .antMatchers("/api/**").hasAnyRole("MANAGE_TASK", "MANAGE_GROUP", "MANAGE_SCHEDULES", "MANAGE_ISSUE")
        
    .and()
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        
    .and()
        .formLogin()
        .loginProcessingUrl("/authenticate").usernameParameter("username")
        .passwordParameter("password").successHandler(restAuthenticationSuccessHandler)
        .failureHandler(restAuthenticationFailureHandler)
        
    .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
        .logoutSuccessUrl("/");
  }
}
