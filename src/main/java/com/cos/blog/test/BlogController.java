package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {
	
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello spring boot</h1>";
	}
	
	@GetMapping("/http/get")
	public String getTest(@RequestParam int id) {
		return "get 요청 : (" + id + ")";
	}
	
	@PostMapping("/http/post") // form-data, x-www-form-urlencoded
	public String postTest(Member m) {	//SpringBoot MessageConverter
		return "post 요청 : (" + m.getId() + ", "+ m.getUsername() +", "+ m.getEmail() +")";
	}
	
	@PostMapping("/http/post2")	// mime : text/plain
	public String postTest2(@RequestBody String text) {
		return "post 요청 text : (" + text +")";
	}	
	
	@PostMapping("/http/post3") // mime : application/json
	public String postTest3(@RequestBody Member m) {
		return "post 요청 : (" + m.getId() + ", "+ m.getUsername() +", "+ m.getEmail() +")";
	}
	
	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
