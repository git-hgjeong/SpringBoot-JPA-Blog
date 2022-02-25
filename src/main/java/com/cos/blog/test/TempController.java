package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {

//	@GetMapping("/temp/home")
//	public String tempHome() {
//		System.out.println("tempHome");
//		return "/home.html";
//	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//prefix : /WEB-INF/views
		//suffix : .jsp
		//full path : /WEB-INF/views/test.jsp
		return "test";
	}	
	
}
