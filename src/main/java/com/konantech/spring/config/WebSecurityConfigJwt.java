package com.konantech.spring.config;

import com.konantech.spring.security.AuthenticationTokenFilter;
import com.konantech.spring.security.RestAuthenticationEntryPoint;
import com.konantech.spring.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigJwt extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfigJwt.class);

	@Autowired
	private AuthService authService;

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(this.authenticationManagerBean());
		return authenticationTokenFilter;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/docs/**", "/");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.headers().disable()
				.csrf().disable()
				.authorizeRequests()

				// 개발임시
				.antMatchers("/v2/**").permitAll()

				// 팝업이용시
				.antMatchers("/popup/**").permitAll()

				// web
				.antMatchers("/user/**").permitAll()

				// server
				.antMatchers("/v2/login").permitAll()
				.antMatchers("/v2/api-docs").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/swagger*/**").permitAll()
				.antMatchers("/docs/**").permitAll()

				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/user/login")
				.failureUrl("/user/login?error")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/", true)
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/user/login?logout");

		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService).passwordEncoder(authService.passwordEncoder());
    }
}
