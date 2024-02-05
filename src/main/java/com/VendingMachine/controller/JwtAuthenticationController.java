package com.VendingMachine.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.VendingMachine.security.JwtTokenUtil;
import com.VendingMachine.model.JwtRequest;
import com.VendingMachine.model.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
		throws Exception {
log.info("authenticationRequest crediantials "+authenticationRequest.getUsername()+" & "+authenticationRequest.getPassword());
	authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

	final UserDetails userDetails = jwtInMemoryUserDetailsService
			.loadUserByUsername(authenticationRequest.getUsername());

	final String token = jwtTokenUtil.generateToken(userDetails);

	// Include the token in the response headers
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.set("Authorization", "Bearer " + token);
	// Return the token in the response body and headers
	return ResponseEntity.ok()
			.headers(responseHeaders)
			.body(new JwtResponse(token));
}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		log.info("authenticationRequest crediantials inside authenticate method "+username+" & "+password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
