package com.pizza.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
<<<<<<< HEAD
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
=======
>>>>>>> 0bedecabb78b8a7ed530b3d507dddf02807ca561
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
<<<<<<< HEAD
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
=======
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
>>>>>>> 0bedecabb78b8a7ed530b3d507dddf02807ca561

import com.pizza.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
<<<<<<< HEAD
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
=======
>>>>>>> 0bedecabb78b8a7ed530b3d507dddf02807ca561
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private DataSource dataSource;

<<<<<<< HEAD
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
=======
	@Autowired
	private MySimpleUrlAuthenticationSuccessHandler successHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
>>>>>>> 0bedecabb78b8a7ed530b3d507dddf02807ca561
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
<<<<<<< HEAD

		// S??t ?????t d???ch v??? ????? t??m ki???m User trong Database.
		// V?? s??t ?????t PasswordEncoder.
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

=======
		// S??t ?????t d???ch v??? ????? t??m ki???m User trong Database.
		// V?? s??t ?????t PasswordEncoder.
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
>>>>>>> 0bedecabb78b8a7ed530b3d507dddf02807ca561
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

<<<<<<< HEAD
		http.csrf().disable().authorizeRequests()

				// C??c trang kh??ng y??u c???u login
				.antMatchers("/mySuFood/dang-nhap").permitAll()

				// Trang /userInfo y??u c???u ph???i login v???i vai tr?? ROLE_USER ho???c ROLE_ADMIN.
				// N???u ch??a login, n?? s??? redirect t???i trang /login.

				// Trang ch??? d??nh cho ADMIN
				.antMatchers("/mySuFood/admin").authenticated();

		// C???u h??nh cho Login Form.
		http.authorizeRequests().and().formLogin()//
				// Submit URL c???a trang login
				.loginProcessingUrl("/j_spring_security_check") // Submit URL
				.loginPage("/dang-nhap")//
				.defaultSuccessUrl("/userAccountInfo")//
				.failureUrl("/login?error=true")//
				.usernameParameter("username")//
				.passwordParameter("password")
				// C???u h??nh cho Logout Page.
				.and().logout().logoutUrl("/mySuFood/logout").logoutSuccessUrl("/mySuFood/trang-chu").permitAll();
=======
		http.authorizeRequests().antMatchers("/dang-nhap", "/403", "/trang-chu", "/").permitAll().antMatchers("/info")
				.hasRole("MEMBER").antMatchers("/admin/**").hasRole("ADMIN").and().formLogin().loginPage("/dang-nhap")
				.usernameParameter("email").passwordParameter("password").successHandler(successHandler)
				.failureUrl("/dang-nhap?error");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/dang-nhap")
				.invalidateHttpSession(true);
>>>>>>> 0bedecabb78b8a7ed530b3d507dddf02807ca561

		// C???u h??nh Remember Me.
		http.authorizeRequests().and() //
				.rememberMe().tokenRepository(this.persistentTokenRepository()) //
				.tokenValiditySeconds(2 * 24 * 60 * 60); // 24h

	}

	// l??u database
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

}