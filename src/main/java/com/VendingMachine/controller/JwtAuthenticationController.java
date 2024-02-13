package com.VendingMachine.controller;

import java.util.List;
import java.util.Objects;

import com.VendingMachine.dto.InventoryDTO;
import com.VendingMachine.model.JwtResponse;
import com.VendingMachine.security.JwtTokenUtil;
import com.VendingMachine.service.InventoryService;
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
import org.springframework.web.bind.annotation.*;

//import com.VendingMachine.security.JwtTokenUtil;
import com.VendingMachine.model.JwtRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	private final InventoryService inventoryService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	public JwtAuthenticationController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}
//
//	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//public ModelAndView createAuthenticationToken(@RequestParam String username, @RequestParam String password, HttpServletResponse response)
//		throws Exception {
//log.info("authenticationRequest crediantials "+username+" & "+password);
//	authenticate(username, password);
//
//	final UserDetails userDetails = jwtInMemoryUserDetailsService
//			.loadUserByUsername(username);
//
//	final String token = jwtTokenUtil.generateToken(userDetails);
//log.info("inside authenticationRequest controller and token generated TOKEN== "+token);
//	// Include the token in the response headers
//	HttpHeaders responseHeaders = new HttpHeaders();
//	responseHeaders.set("Authorization", "Bearer " + token);
//	Cookie cookie = new Cookie("JWT-TOKEN", token);
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
//	List<InventoryDTO> list = inventoryService.getListOfAllInventory();
//
//	ModelAndView model = new ModelAndView();
//	model.addObject("list", list);
//	model.setViewName("getInventoryList");
//	// Return the token in the response body and headers
//	return model;
//}

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


	//////////////////////////////////correct working one above createAuthenticationToken method is changed for testing

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletResponse response)
			throws Exception {
		log.info("authenticationRequest crediantials "+authenticationRequest.getUsername()+" & "+authenticationRequest.getPassword());
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		log.info("inside authenticationRequest controller and token generated TOKEN== "+token);
		// Include the token in the response headers
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", "Bearer " + token);

		// Return the token in the response body and headers
		return ResponseEntity.ok()
				.headers(responseHeaders)
				.body(new JwtResponse(token));
	}
}
