package com.pizza.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pizza.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private MySimpleUrlAuthenticationSuccessHandler successHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Sét đặt dịch vụ để tìm kiếm User trong Database.
		// Và sét đặt PasswordEncoder.
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();

		http.authorizeRequests().antMatchers("/dang-nhap", "/403", "/trang-chu", "/", "/pay/**").permitAll().antMatchers("/info")
				.hasRole("MEMBER").antMatchers("/admin/**").hasRole("ADMIN").and().formLogin().loginPage("/dang-nhap")
				.usernameParameter("email").passwordParameter("password").successHandler(successHandler)
				.failureUrl("/dang-nhap?error");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/dang-nhap")
				.invalidateHttpSession(true);

		// Cấu hình Remember Me.
		http.authorizeRequests().and() //
				.rememberMe().tokenRepository(this.persistentTokenRepository()) //
				.tokenValiditySeconds(2 * 24 * 60 * 60); // 24h

	}

	// lưu database
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

}