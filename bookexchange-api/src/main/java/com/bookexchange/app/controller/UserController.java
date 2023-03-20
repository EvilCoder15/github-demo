package com.bookexchange.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bookexchange.app.model.User;
import com.bookexchange.app.repository.UserRepository;


@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
	
	@PostMapping("/signup")
	public void signUp(@RequestBody User user)
	{
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	@GetMapping("/login")
	public String login()
	{
		return "Login success!";
	}
//	@PutMapping("/admin/verify-user")
//	public String verifyUser(@RequestHeader HttpHeaders header)
//	{
//		
//	}

}
