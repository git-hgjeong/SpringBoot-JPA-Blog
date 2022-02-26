package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyController {

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("User : "+ user.toString());
		user.setRole(RoleType.USER);
		userRepository.save(user);		
		return "회원가입 완료.";
	}
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//1) 이 방식은 null이 리턴될수 있다. 예외처리가 필요하다.
		//User user = userRepository.findById(id).get();
		
		//2) 빈 객체를 리턴한다. 이 방법도 그다지 적절하지 않다.
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			@Override
//			public User get() {
//				// TODO Auto-generated method stub
//				return new User();
//			}
//		});

		//3) 권장하는 방법. IllegalArgumentException을 사용한다. 에러화면은 추후 AOP를 사용하여 별도 처리한다.
//		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				// TODO Auto-generated method stub
//				return new IllegalArgumentException("해당 유저가 존재하지 않습니다!");
//			}
//		});		

		//4) 편한 방법. 람다식을 사용하고 IllegalArgumentException을 사용한다. 에러화면은 추후 AOP를 사용하여 별도 처리한다.
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("해당 유저가 존재하지 않습니다. ID : "+ id);
		});				
		
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		List<User> list = userRepository.findAll();
		return list;
	}
	
	//http://localhost:8000/blog/dummy/user/list?page=0
	@GetMapping("/dummy/user/list")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		
		Page<User> pagingUsers = userRepository.findAll(pageable);
		List<User> list = pagingUsers.getContent();
		
		return list;
	}	
	
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		
		System.out.println(requestUser.toString());
		
		return requestUser;
	}
	
}
