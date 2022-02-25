package com.cos.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
@Data
//@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class Member {
	
	private int id;
	private String username;
	private String password;
	private String email;
	
	
	@Builder
	public Member(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	
}
