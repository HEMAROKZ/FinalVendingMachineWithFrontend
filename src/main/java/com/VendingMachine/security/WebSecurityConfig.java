package com.VendingMachine.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
//
//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		// We don't need CSRF for this example
//		httpSecurity.csrf().disable()
//				.authorizeRequests()
//				.antMatchers("/authenticate","/login","/home","/product/**"," /Inventoryproduct/**","/purchasemultipleproductpage","/purchase-Inventryitem","/purchaseproductpage"," /purchaseproduct").permitAll() // Allow access to images without authentication
//				.antMatchers("/getAllInventory","/delete/**", "/addinventoryitem", "/add-Inventryitem", "/update/user/**").authenticated()
//				.and()
//				.formLogin()
//				.loginPage("/login") // Specify the custom login page URL
//				.defaultSuccessUrl("/getAllInventory")
//				.failureUrl("/login?error=true")
//				.permitAll()
//				.and()
//				.apply(new JwtConfigurer(jwtRequestFilter));
////
////		// Add a fil ter to validate the tokens with every request
////		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//	}
//
//

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/authenticate","/login","/home","/product/**"," /Inventoryproduct/**","/purchasemultipleproductpage","/purchase-Inventryitem","/purchaseproductpage"," /purchaseproduct").permitAll(). // Allow access to images without authentication
				// all other requests need to be authenticated
						anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login") // Specify the custom login page URL
				.defaultSuccessUrl("/getAllInventory")
				.failureUrl("/login?error=true")
				.permitAll()
				.and()
				// make sure we use stateless session; session won't be used to
				// store user's state.
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
